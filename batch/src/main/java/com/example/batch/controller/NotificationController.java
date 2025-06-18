package com.example.batch.controller;

import com.example.batch.dto.JobCompletionEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/sendNotif")
    @SendTo("/topic/notifications")
    public JobCompletionEvent sendNotification (JobCompletionEvent notification){
        return notification;
    }
}
