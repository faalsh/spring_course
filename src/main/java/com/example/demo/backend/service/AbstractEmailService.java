package com.example.demo.backend.service;

import com.example.demo.web.domain.frontend.FeedbackModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEmailService.class);

    @Value("${default.to.address}")
    private String defaultToAddress;

    protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackModel(FeedbackModel feedbackModel) {
        LOG.debug("prepare message {}", feedbackModel);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(defaultToAddress);
        message.setFrom(feedbackModel.getEmail());
        message.setSubject("Feedback from " + feedbackModel.getFirstName());
        message.setText(feedbackModel.getFeedback());
        return message;
    }

    @Override
    public void sendFeedbackEmail(FeedbackModel feedbackModel) {
        sendGenericEmailMessage(prepareSimpleMailMessageFromFeedbackModel(feedbackModel));
    }
}
