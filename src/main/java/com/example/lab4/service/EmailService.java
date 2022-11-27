package com.example.lab4.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${email.to}")
    private String toEmailAddress;

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmailAddress);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
