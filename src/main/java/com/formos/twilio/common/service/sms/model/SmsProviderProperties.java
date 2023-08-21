package com.formos.twilio.common.service.sms.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sms-provider")
public class SmsProviderProperties {

    private Twilio twilio;
    private String type;

    @Data
    public static class Twilio {
        private String accountSid;
        private String authToken;
        private String messageServiceSid;
        private String verifyServiceSid;
        private String senderNumber;
    }

}
