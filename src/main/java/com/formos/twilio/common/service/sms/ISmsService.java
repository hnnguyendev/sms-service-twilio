package com.formos.twilio.common.service.sms;

public interface ISmsService {

    void sendMessage(String to, String message);

    void generateOtp(String to);

    boolean verifyUserOtp(String to, String code);

}
