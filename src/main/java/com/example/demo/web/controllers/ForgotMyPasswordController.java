package com.example.demo.web.controllers;

import com.example.demo.backend.persistence.domain.backend.PasswordResetToken;
import com.example.demo.backend.persistence.domain.backend.User;
import com.example.demo.backend.service.EmailService;
import com.example.demo.backend.service.I18nService;
import com.example.demo.backend.service.PasswordResetTokenService;
import com.example.demo.backend.service.UserService;
import com.example.demo.utils.UserUtils;
import com.example.demo.web.domain.frontend.ResetPasswordModel;
import com.example.demo.web.domain.frontend.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;

@RestController
public class ForgotMyPasswordController {

    private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);
    private static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "forgotmypassword.email.text";


    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private I18nService i18nService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Value("${webmaster.email}")
    private String webmasterEmail;

    @GetMapping("/api/public/forgotPassword")
    public ResponseEntity<StandardResponse> forgotPassword(HttpServletRequest request, @RequestParam("email") String email){
        LOG.info("resetting password for email {}", email);
        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(email);

        if(null == passwordResetToken){
            LOG.warn("Couldn't find a password reset token for email {}", email);
        } else {

            User user = passwordResetToken.getUser();
            String token = passwordResetToken.getToken();
            LOG.info("Token value {} created for username {}", passwordResetToken.getToken(), passwordResetToken.getUser().getUsername());

            String resetPasswordUrl = UserUtils.createPasswordResetUrl(request, user.getId(), token);
            LOG.debug("Reset password url {}", resetPasswordUrl);

            String emailText = i18nService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME, request.getLocale());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("How to reset your password");
            mailMessage.setTo(user.getEmail());
            mailMessage.setFrom(webmasterEmail);
            mailMessage.setText(emailText + "\r\n" + resetPasswordUrl);

            emailService.sendGenericEmailMessage(mailMessage);
        }

        return new ResponseEntity<StandardResponse>(new StandardResponse(true), HttpStatus.OK);
    }

    @PostMapping("/api/public/resetPassword")
    public ResponseEntity<StandardResponse> resetPassword(@RequestBody ResetPasswordModel resetPasswordModel){

        String email = resetPasswordModel.getEmail();
        String token = resetPasswordModel.getToken();
        String newPassword = resetPasswordModel.getNewPassword();

        // Check if request is not empty and valid
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(email)){
            LOG.error("invalid email {} or token {}", email, token);
            return new ResponseEntity<StandardResponse>(new StandardResponse(false, "invalid email or token"), HttpStatus.BAD_REQUEST);
        }

        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

        // Check if token is found
        if(null == passwordResetToken){
            LOG.error("token couldn't be found with value {} for email {} ", token, email);
            return new ResponseEntity<StandardResponse>(new StandardResponse(false, "invalid email or token"), HttpStatus.BAD_REQUEST);
        }

        User user = passwordResetToken.getUser();

        // Check if token matches the user id
        if(! user.getEmail().equals(email)){
            LOG.error("The email {} doesn't match the email {} asssociated with the token {} ", email, user.getEmail(), token);
            return new ResponseEntity<StandardResponse>(new StandardResponse(false, "invalid email or token"), HttpStatus.BAD_REQUEST);
        }

        // Check if token is not expired
        if(LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())){
            LOG.error("The token {} has expired", token);
            return new ResponseEntity<StandardResponse>(new StandardResponse(false, "invalid email or token"), HttpStatus.BAD_REQUEST);
        }

        userService.updateUserPassword(email, newPassword);
        LOG.info("password successfully updated for user {}", user.getUsername());

        // TODO delete the token
//        passwordResetTokenService.deleteToken(token);
//        LOG.info("token {} successfully deleted", token);

        // return success
        return new ResponseEntity<StandardResponse>(new StandardResponse(true, ""), HttpStatus.OK);

    }


}
