package com.labourseSearching.Service.JobQuery;

import com.labourseSearching.DTO.KafkaEvent.JobAcceptedEvent;
import com.labourseSearching.Entity.JobPost.JobAcceptedLabour;
import com.labourseSearching.Repository.JobPostRespository.JobAcceptedLabourRepository;
import com.labourseSearching.Service.KafkaProducer.JobAcceptedProducer;
import com.labourseSearching.websocket.WebSocketNotificationService;
import org.springframework.stereotype.Service;

@Service
public class JobAcceptanceServiceImpl implements JobAcceptanceService {

    private final RedisLuaJobAcceptanceService luaService;
    private final JobAcceptedLabourRepository acceptedRepo;
    private final CustomerNotificationService customerNotificationService;
    private final JobAcceptedProducer jobAcceptedProducer;
    private final WebSocketNotificationService webSocketNotificationService;


    public JobAcceptanceServiceImpl(
            RedisLuaJobAcceptanceService luaService,
            JobAcceptedLabourRepository acceptedRepo,
            CustomerNotificationService customerNotificationService, JobAcceptedProducer jobAcceptedProducer, WebSocketNotificationService webSocketNotificationService
    ) {
        this.luaService = luaService;
        this.acceptedRepo = acceptedRepo;
        this.customerNotificationService = customerNotificationService;
        this.jobAcceptedProducer = jobAcceptedProducer;
        this.webSocketNotificationService = webSocketNotificationService;
    }

    @Override
    public boolean acceptJob(
            Long jobId,
            String labourType,
            Long labourId,
            Long customerId
    ) {
        String key = "job:req:count:" + jobId;

        // 🔥 ATOMIC REDIS LUA CALL
        long result = luaService.decrement(key, labourType);

        if (result < 0) {
            return false;
        }

        if (result == 0) {
            System.out.println("All slots filled for " + labourType);
        }

        // ✅ Save accepted labour
        acceptedRepo.save(
                new JobAcceptedLabour(jobId, labourId, labourType)
        );

        // 🔔 WebSocket Notification
        customerNotificationService.notifyCustomer(
                customerId,
                "Labour " + labourId + " accepted your job #" + jobId
        );

        jobAcceptedProducer.publish(
                new JobAcceptedEvent(
                        jobId,
                        labourId,
                        customerId,
                        labourType
                )
        );
        webSocketNotificationService.notifyCustomer(
                customerId,
                "Labour accepted your job (ID: " + labourId + ")"
        );


        return true;
    }
}