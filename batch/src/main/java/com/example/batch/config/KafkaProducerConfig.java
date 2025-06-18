package com.example.batch.config;



//@Configuration
public class KafkaProducerConfig {
/*
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, JobCompletionEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.TYPE_MAPPINGS,
                "com.example.batch.dto.JobCompletionEvent:com.notification.Notification.dto.JobCompletionEvent");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, com.notification.Notification.dto.JobCompletionEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }*/
}
