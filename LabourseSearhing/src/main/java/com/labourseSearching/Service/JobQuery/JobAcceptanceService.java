package com.labourseSearching.Service.JobQuery;

public interface JobAcceptanceService {
    boolean acceptJob(Long jobId, String labourType, Long id, Long customerId);
}