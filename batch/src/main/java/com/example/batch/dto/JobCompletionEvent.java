package com.example.batch.dto;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCompletionEvent implements Serializable  {

    private String jobName;
    private String status;
    private LocalDateTime completionTime;
    private long recordsProcessed;
    private String message;

}

