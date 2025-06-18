package com.example.batch.config;

import com.example.batch.listener.JobCompletionNotificationListener;
import com.example.batch.model.Transaction;
import com.example.batch.repository.TransactionRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration


public class BatchConfig {
    private final TransactionRepository repository;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final JobCompletionNotificationListener jobCompletionListener;

    // Manually add constructor
    public BatchConfig(TransactionRepository repository, JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, JobCompletionNotificationListener jobCompletionListener ) {
        this.repository = repository;
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.jobCompletionListener = jobCompletionListener;
    }

    //Item Reader
    @Bean
    public FlatFileItemReader<Transaction> reader (){
        FlatFileItemReader<Transaction> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("transaction.csv"));
        itemReader.setName("TransactionFileReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }


    /*@Bean
    public CompositeItemProcessor<Transaction, Transaction> compositeProcessor() {
        CompositeItemProcessor<Transaction, Transaction> compositeProcessor = new CompositeItemProcessor<>();
        compositeProcessor.setDelegates(Arrays.asList(new TransactionProcessor(), new FinancialTransactionProcessor()));
        return compositeProcessor;
    }
*/

    @Bean
    public ItemProcessor<Transaction, Transaction> transactionProcessor() {
        return new TransactionProcessor();
    }


    @Bean
    public RepositoryItemWriter<Transaction> writer(){
        RepositoryItemWriter<Transaction> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("save"); // we want to execute the same methode of the repository
        return writer;

    }
    @Bean
    public Step step1(JobRepository jobRepository,PlatformTransactionManager transactionManager)  {
        return new StepBuilder("csv-step",jobRepository).<Transaction, Transaction>chunk(10,transactionManager)
                .reader(reader())
                .processor(transactionProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob(JobRepository jobRepository,PlatformTransactionManager transactionManager){
        return new JobBuilder("importTransactions",jobRepository)
                .listener(jobCompletionListener)
                .flow(step1(jobRepository,transactionManager))
                .end()
                .build();
    }




    private LineMapper<Transaction> lineMapper() { //which tells Spring Batch how to take a line from your CSV file and convert it into a Transaction object
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); //a DelimitedLineTokenizer, which splits a line based on a specific delimiter.
        lineTokenizer.setDelimiter("\t");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("transaction_id", "account_number", "transaction_date", "transaction_type", "transaction_amount"); //etNames(...) = match Transaction class fields

        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Transaction.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }}

