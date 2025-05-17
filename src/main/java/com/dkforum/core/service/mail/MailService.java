package com.dkforum.core.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;
    @Value("${app.mail.from}")
    private String from;

    public boolean sendSimple(MailDetails details) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(from);
            message.setTo(details.getRecipients());
            message.setSubject(details.getSubject());
            message.setText(details.getContent());

            sender.send(message);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendMime(MailDetails details){
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(details.getRecipients());
            mimeMessageHelper.setText(details.getContent());
            mimeMessageHelper.setSubject(details.getSubject());

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            // Sending the mail
            sender.send(mimeMessage);
            return true;
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return false;
        }
    }

}
