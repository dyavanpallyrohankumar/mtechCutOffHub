package com.mtech.addmissions.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    public JavaMailSender mailSender;

    public void sendOtp(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Admin Login OTP");
        message.setText(
                "Your OTP for admin login is: " + otp +
                        "\nValid for 5 minutes.\n\nIf not requested, ignore this email.");
        mailSender.send(message);
    }
}
