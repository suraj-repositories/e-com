package com.oranbyte.ecom.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailUtils {

	@Autowired
    private JavaMailSender emailSender;
	
	@Value("${spring.mail.username}")
    private String emailUsername; 
	
    public void sendSimpleMessage(String to, String subject, String text, List<String> list) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailUsername);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if (list != null && !list.isEmpty()) {
            message.setCc(getCcArray(list));
        }

        log.info("sending email");
        emailSender.send(message);
    }

    private String[] getCcArray(List<String> ccList) {
        return ccList.toArray(new String[0]);
    }

    public void forgotMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailUsername);
        helper.setTo(to);
        helper.setSubject(subject);

        String htmlMessage = "<h3>Your Login details are - </h3>"
                + "<p>Email : <b>" + to + "</b></p>"
                + "<p>Password : <b>" + password + "</b></p>";

        helper.setText(htmlMessage, true);

        emailSender.send(message);
    }
}