package com.notification.Notification.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplate {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String subject;
    private Integer version;
    @Column(columnDefinition = "TEXT")
    private String htmlContent;
    private LocalDate createdAt;
    private boolean active;




    
}
