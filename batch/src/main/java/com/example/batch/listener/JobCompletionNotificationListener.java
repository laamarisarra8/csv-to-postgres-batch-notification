package com.example.batch.listener;

import com.example.batch.dto.JobCompletionEvent;
import com.example.batch.producer.JobCompletionNotificationProducer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
//@AllArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Autowired
    private  JobCompletionNotificationProducer producer;
    @Autowired
    private  SimpMessagingTemplate messagingTemplate;


    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job '{}' starting. Execution id: {}",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getId());
    }



    public JobCompletionNotificationListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Preserve your existing logic for job details
        String jobName = jobExecution.getJobInstance().getJobName();
        String status = jobExecution.getStatus().toString();
        long recordsProcessed = jobExecution.getStepExecutions().stream()
                .mapToLong(StepExecution::getWriteCount)
                .sum();

        log.info("Job '{}' completed with status: {}. Records processed: {}",
                jobName, status, recordsProcessed);

        String message = jobExecution.getStatus() == BatchStatus.COMPLETED
                ? "Job completed successfully"
                : "Job failed - " + jobExecution.getExitStatus().getExitDescription();

        // Creating a JobCompletionEvent
        JobCompletionEvent notification = new JobCompletionEvent();
        notification.setJobName(jobName);
        notification.setStatus(status);
        notification.setMessage(message);
        notification.setCompletionTime(LocalDateTime.now());
        notification.setRecordsProcessed(recordsProcessed);

        //log.info("Sending WebSocket notification: {}", notification);

        //log.info("Attempting to send to /topic/notifications: {}", notification);


        // Sending via WebSocket
        messagingTemplate.convertAndSend("/topic/notifications", notification);

        //Sending via KAFKA
        producer.sendNotification(notification);
    }

    /*@Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        String status = jobExecution.getStatus().toString();
        long recordsProcessed = jobExecution.getStepExecutions().stream()
                .mapToLong(StepExecution::getWriteCount)
                .sum();

        log.info("Job '{}' completed with status: {}. Records processed: {}",
                jobName, status, recordsProcessed);

        String message = jobExecution.getStatus() == BatchStatus.COMPLETED
                ? "Job completed successfully"
                : "Job failed - " + jobExecution.getExitStatus().getExitDescription();

        JobCompletionEvent event = new JobCompletionEvent(
                jobName,
                status,
                LocalDateTime.now(),
                recordsProcessed,
                message
        );

        log.info("Generated JobCompletionEvent: {}", event);

        producer.sendNotification(event);
    }*/




}
