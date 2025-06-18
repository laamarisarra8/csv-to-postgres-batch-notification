package com.example.batch.producer;

import com.notification.Notification.dto.JobCompletionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobCompletionNotificationProducer {
   // private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationProducer.class);


    private final KafkaTemplate<String, JobCompletionEvent> kafkaTemplate;

    @Value("${kafka.topic.batch.completion}")
    private String topicName;

    public void sendNotification(com.example.batch.dto.JobCompletionEvent event) {
        log.info("Sending job completion notification: {}", event);



        log.info("Preparing to send job completion notification.");
        log.info("Job Name: {}", event.getJobName());
        log.info("Status: {}", event.getStatus());
        log.info("Records Processed: {}", event.getRecordsProcessed());
        log.info("Message: {}", event.getMessage());
        log.info("Event Timestamp: {}", event.getCompletionTime());

      // Converting to the consumer’s expected format
        com.notification.Notification.dto.JobCompletionEvent consumerEvent =
                new com.notification.Notification.dto.JobCompletionEvent(
                        event.getJobName(),
                        event.getStatus(),
                        event.getCompletionTime(),
                        event.getRecordsProcessed(),
                        event.getMessage()
                );
        kafkaTemplate.send(topicName, consumerEvent);
        log.info("Job completion notification sent successfully to topic: {}", topicName);


    }}
