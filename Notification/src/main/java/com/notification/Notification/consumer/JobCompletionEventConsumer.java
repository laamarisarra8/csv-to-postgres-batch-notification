package com.notification.Notification.consumer;

import com.notification.Notification.dto.JobCompletionEvent;

import com.notification.Notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class JobCompletionEventConsumer {

    private final EmailService emailService;
   /* public JobCompletionEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }*/

    @KafkaListener(topics = "${kafka.topic.batch.completion}" , groupId = "${spring.kafka.consumer.group-id}")
    public void listen(JobCompletionEvent event) {


        log.info("Received job completion event: {}", event);

        //logging before i send the email ---cheking wakahaw
        log.info("Job Name: {}", event.getJobName());
        log.info("Status: {}", event.getStatus());
        log.info("Completion Time: {}", event.getCompletionTime());
        log.info("Records Processed: {}", event.getRecordsProcessed());




        emailService.sendJobCompletionEmail(event);
    }

}
