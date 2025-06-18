package com.notification.Notification.service;

import com.notification.Notification.dto.JobCompletionEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${notification.email.recipient}")
    private String recipientEmail;


    @Async
    public void sendJobCompletionEmail(JobCompletionEvent event) {
        log.info("Preparing to send job completion email for job: {}", event.getJobName());

        Context context = new Context();
        context.setVariable("event", event);
        context.setVariable("isSuccess", "COMPLETED".equals(event.getStatus()));

        String subject = String.format("Batch Job %s: %s",
                event.getJobName(),
                event.getStatus());

        String emailContent = templateEngine.process("job-completion", context);

        try {
            sendEmail(recipientEmail , subject, emailContent);
            log.info("Job completion email sent successfully");
        } catch (MessagingException e) {
            log.error("Failed to send job completion email", e);
        }


    }

    private void sendEmail(String recipient, String subject, String content)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("laamari.sa99@gmail.com");
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(content, true);

        //Adding Attachments :
        FileSystemResource photo1 = new FileSystemResource(new File(System.getProperty("user.home") +"/Downloads/images/Roadmap.png"));
        FileSystemResource photo2 = new FileSystemResource(new File(System.getProperty("user.home") +"/Downloads/images/Sujet Wevioo.jpg"));
        FileSystemResource pdf = new FileSystemResource(new File(System.getProperty("user.home") +"/Downloads/images/COOPJ.pdf"));
        //Attaching Them:
        helper.addAttachment(photo1.getFilename(),photo1);
        helper.addAttachment(photo2.getFilename(),photo2);
        helper.addAttachment(pdf.getFilename(),pdf);

        mailSender.send(message);
    }
}
