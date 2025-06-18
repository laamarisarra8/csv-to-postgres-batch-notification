package com.example.batch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private Long transaction_id;

    @Column(name = "account_number")
    private Long account_number;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date")
    private String transaction_date;

    @Column(name = "transaction_type")
    private String transaction_type;

    @Column(name = "transaction_amount")
    private double transaction_amount;
   // private double feeApplied;

    //private Status status;




    //private String channel;


}
