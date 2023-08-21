package com.formos.twilio.controller;

import com.formos.twilio.common.model.request.VerifyUserRequest;
import com.formos.twilio.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sms")
@Validated
public class SmsController {

    private final SmsService smsService;

    @GetMapping(value = "/send-message")
    public ResponseEntity<String> sendMessage() {
        smsService.sendMessage();
        return new ResponseEntity<>("Your message has been sent", HttpStatus.OK);
    }

    @GetMapping(value = "/generate-otp")
    public ResponseEntity<String> generateOtp() {
        smsService.generateOtp();
        return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Boolean> verifyUserOtp(@RequestBody VerifyUserRequest request) {
        return new ResponseEntity<>(smsService.verifyUserOtp(request), HttpStatus.OK);
    }
}
