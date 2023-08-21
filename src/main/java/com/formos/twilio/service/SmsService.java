package com.formos.twilio.service;

import com.formos.twilio.common.model.request.VerifyUserRequest;
import com.formos.twilio.common.service.sms.ISmsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SmsService {

    private final ISmsService smsService;

    public void sendMessage() {
        smsService.sendMessage("+84xxxxxxxxx", "Demo Twilio Message");
    }

    public void generateOtp() {
        smsService.generateOtp("+84xxxxxxxxx");
    }

    public boolean verifyUserOtp(VerifyUserRequest request) {
        return smsService.verifyUserOtp(request.getPhoneNumber(), request.getCode());
    }
}
