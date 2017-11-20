package com.example.demo.web.controllers;

import com.example.demo.backend.service.EmailService;
import com.example.demo.backend.service.FeedbackService;
import com.example.demo.web.domain.frontend.FeedbackModel;
import com.example.demo.web.domain.frontend.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class FeedbackController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/api/feedback")
    public ResponseEntity<StandardResponse> createFeedback(@RequestBody FeedbackModel feedbackModel) {
        LOG.debug("Creating feedback {}", feedbackModel);

        emailService.sendFeedbackEmail(feedbackModel);

        return new ResponseEntity<StandardResponse>(new StandardResponse(true), HttpStatus.OK);
    }


}
