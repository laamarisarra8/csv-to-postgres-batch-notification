package com.example.batch.controller;

import com.example.batch.config.FinancialTransactionProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
//@RequestMapping("/startjob")
public class TransactionController {



    private final JobLauncher jobLauncher;
    private  final Job job;


    public TransactionController(JobLauncher jobLauncher, Job job ) {
        this.jobLauncher = jobLauncher;
        this.job = job;

    }


    @PostMapping
    public void importCsvToDBJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/startJob")
    public BatchStatus load() throws Exception {
        Map<String, JobParameter<?>> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis(), Long.class, true));
        JobParameters jobParameters = new JobParameters(params);
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        while (jobExecution.isRunning()) {
            log.info("..........");
        }
        return jobExecution.getStatus();
    }


    /*@GetMapping("/analytics")
    public Map<String, Double> analytics(){
        Map<String, Double> map = new HashMap<>();
        map.put("totalCredit", analyticsProcessor.getTotalCredit());
        map.put("totalDebit", analyticsProcessor.getTotalDebit());
        return map;
    }*/
    /*@GetMapping("/analytics")
    public Map<String, Double> analytics() {
        Map<String, Double> map = new HashMap<>();
        // Both approaches work:

        // Option A: Via instance (works due to singleton)
        map.put("totalCredit", FinancialTransactionProcessor.getTotalCredit());
        map.put("totalDebit", FinancialTransactionProcessor.getTotalDebit());

        // Option B: Direct static access (more explicit)
        // map.put("totalCredit", FinancialTransactionProcessor.getTotalCredit());
        // map.put("totalDebit", FinancialTransactionProcessor.getTotalDebit());

        return map;
    }*/
    }

