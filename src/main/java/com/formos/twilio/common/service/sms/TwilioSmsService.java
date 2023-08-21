package com.formos.twilio.common.service.sms;

import com.formos.twilio.common.service.sms.model.SmsProviderProperties;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAsync(proxyTargetClass = true)
public class TwilioSmsService implements ISmsService {

    @Value("${sms-provider.phone-number-default}")
    private String phoneNumberDefault;

    @Value("${sms-provider.use-phone-number-default}")
    private String usePhoneNumberDefault;

    @Value("${sms-provider.channel-type}")
    private String channelType;

    private final SmsProviderProperties properties;

    private static final String REGEX_PHONE;

    private static final String USA_COUNTRY_CODE;

    static {
        REGEX_PHONE = "[^a-zA-Z0-9]";
        USA_COUNTRY_CODE = "+1";
    }

    @Override
    public void sendMessage(String to, String contents) {
                    String phone = USA_COUNTRY_CODE + to.replaceAll(REGEX_PHONE, "");
        String type = properties.getType().toLowerCase();
        switch (type) {
            case "log":
                System.out.printf("Would send SMS to: %s With message: %s%n", phone, contents);
                break;
            case "twilio":
                String phoneNumber = Boolean.TRUE.equals(Boolean.parseBoolean(usePhoneNumberDefault)) ? "+" + phoneNumberDefault : phone;
                Message.creator(new com.twilio.type.PhoneNumber(phoneNumber), properties.getTwilio().getMessageServiceSid(), contents).create();
                break;
            default:
                System.out.printf("Send SMS to: %s With message: %s%n", phone, contents);
        }
    }

    @Override
    public void generateOtp(String to) {
        Verification verification = Verification.creator(
                properties.getTwilio().getVerifyServiceSid(),
                to,
                channelType)
//                .setCustomFriendlyName("Formos") // paid feature
                .create();

        System.out.println(verification.getStatus());

        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());
    }

    @Override
    public boolean verifyUserOtp(String to, String code) {
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            properties.getTwilio().getVerifyServiceSid())
                    .setTo(to)
                    .setCode(code)
                    .create();

            System.out.println(verificationCheck.getStatus());

            return Objects.equals(verificationCheck.getStatus(), "approved");
        } catch (Exception e) {
            return false;
        }
    }

}
