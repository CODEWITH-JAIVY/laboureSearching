package com.labourseSearching.DTO.KafkaEvent;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class JobAcceptedEvent {

    private Long jobId;
    private Long labourId;
    private Long customerId;
    private String labourType;

    public JobAcceptedEvent() {}

    public JobAcceptedEvent(Long jobId, Long labourId, Long customerId, String labourType) {
        this.jobId = jobId;
        this.labourId = labourId;
        this.customerId = customerId;
        this.labourType = labourType;
    }

    // getters setters
}