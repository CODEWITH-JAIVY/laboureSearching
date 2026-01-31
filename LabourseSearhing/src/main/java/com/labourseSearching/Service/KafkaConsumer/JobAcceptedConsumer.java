package com.labourseSearching.Service.KafkaConsumer;

import com.labourseSearching.DTO.KafkaEvent.JobAcceptedEvent;
import com.labourseSearching.Service.JobQuery.CustomerNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JobAcceptedConsumer {

    private final CustomerNotificationService notificationService;

    public JobAcceptedConsumer(CustomerNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "job-accepted-topic",
            groupId = "labourse-group"
    )
    public void consume(JobAcceptedEvent event) {

        notificationService.notifyCustomer(
                event.getCustomerId(),
                "Labour " + event.getLabourId() +
                        " accepted your job #" + event.getJobId()
        );
    }
}