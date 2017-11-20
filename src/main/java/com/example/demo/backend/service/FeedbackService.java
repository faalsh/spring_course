package com.example.demo.backend.service;

import com.example.demo.web.domain.frontend.FeedbackModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    private static FeedbackModel feedbackModel1 = new FeedbackModel();

    static {
        feedbackModel1.setEmail("alshunaiber@gmail.com");
        feedbackModel1.setFirstName("Fahad");
        feedbackModel1.setLastName("Alshunaiber");
        feedbackModel1.setFeedback("hello");
    }

    public List<FeedbackModel> retrieveAllFeedbacks() {
        List<FeedbackModel> list = new ArrayList<FeedbackModel>();
        list.add(feedbackModel1);
        return list;
    }

    public FeedbackModel getFeedback() {
        return feedbackModel1;
    }
}
