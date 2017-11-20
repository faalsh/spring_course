package com.example.demo.backend.service;

import com.example.demo.web.domain.frontend.FeedbackModel;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public void sendFeedbackEmail(FeedbackModel feedbackModel);

    public void sendGenericEmailMessage(SimpleMailMessage message);

}
