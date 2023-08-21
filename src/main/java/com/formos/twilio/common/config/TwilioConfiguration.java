package com.formos.twilio.common.config;

import com.formos.twilio.common.service.sms.model.SmsProviderProperties;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class TwilioConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(TwilioConfiguration.class);

    private final SmsProviderProperties properties;

    public TwilioConfiguration(SmsProviderProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Twilio.init(properties.getTwilio().getAccountSid(), properties.getTwilio().getAuthToken());
        log.debug("Twilio initialized");
    }

}
