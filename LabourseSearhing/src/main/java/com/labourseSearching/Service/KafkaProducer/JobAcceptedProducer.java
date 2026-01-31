package com.labourseSearching.Service.KafkaProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labourseSearching.DTO.KafkaEvent.JobAcceptedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class JobAcceptedProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public JobAcceptedProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    // 🔥 SINGLE PUBLIC METHOD
    public void publish(JobAcceptedEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("job-accepted-topic", json);
        } catch (Exception e) {
            throw new RuntimeException("Kafka publish failed", e);
        }
    }
}