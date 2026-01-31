package com.labourseSearching.Entity.LocationLiveTracing;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class LiveLocation {

    private Double latitude;
    private Double longitude;

    private LocalDateTime updatedAt;
}