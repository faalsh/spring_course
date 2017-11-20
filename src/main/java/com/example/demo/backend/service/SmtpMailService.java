package com.example.demo.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class SmtpMailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpMailService.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage message) {
        LOG.debug("sending smtp mail {}", message);
        mailSender.send(message);
        LOG.info("mail sent");
    }
}
