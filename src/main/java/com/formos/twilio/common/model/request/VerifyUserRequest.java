package com.formos.twilio.common.model.request;

import lombok.Value;

@Value
public class VerifyUserRequest {

    String phoneNumber;

    String code;

}
