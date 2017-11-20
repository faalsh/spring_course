package com.example.demo.utils;

import com.example.demo.backend.persistence.domain.backend.User;
import com.example.demo.web.domain.frontend.BasicAccountPayload;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

    private UserUtils() {
        throw new AssertionError("none init");
    }

    public static User createBasicUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("secret");
        user.setEmail(email);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNumber("123456");
        user.setCountry("SA");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://profileimage/fake/url");

        return user;
    }

    public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token){
        String passwordResetUrl =
                request.getScheme() +
                        "://"+
                        request.getServerName() +
                        ":" +
                        request.getServerPort() +
                        request.getContextPath() +
                        "/resetPassword?id=" +
                        userId +
                        "&token=" +
                        token;
        return passwordResetUrl;


    }

    public static <T extends BasicAccountPayload> User fromWebUserToDomainUser(T frontEndPayload){
        User user = new User();
        user.setUsername(frontEndPayload.getUserName());
        user.setPassword(frontEndPayload.getPassword());
        user.setFirstName(frontEndPayload.getFirstName());
        user.setLastName(frontEndPayload.getLastName());
        user.setEmail(frontEndPayload.getEmail());
        user.setPhoneNumber(frontEndPayload.getPhoneNumber());
        user.setCountry(frontEndPayload.getCountry());
        user.setEnabled(true);
        user.setDescription(frontEndPayload.getDescription());

        return user;

    }
}
