package com.dkforum.core.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;



@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;
    @Value("${app.mail.from}")
    private String from;

    public boolean send(MailMessage msg) {
        try{
            if(msg instanceof MimeMessage){
                sender.send((MimeMessage)msg);
            }else if(msg instanceof SimpleMailMessage) {
                sender.send((SimpleMailMessage) msg);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
