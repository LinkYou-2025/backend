package com.umc.linkyou.service.curation.utils;

import com.umc.linkyou.service.curation.CurationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CurationBatchScheduler {

    private final CurationService curationService;

    @Scheduled(cron = "0 5 0 1 * *", zone = "Asia/Seoul")
    public void runMonthlyCurationBatch() {
        System.out.println("✅ 자동 큐레이션 생성 실행");
        curationService.generateMonthlyCurationForAllUsers();
    }
}

//테스트용
//@Component
//@RequiredArgsConstructor
//public class CurationBatchScheduler {
//
//    private final CurationService curationService;
//
//    @PostConstruct
//    public void scheduleOnceAfterFiveMinutes() {
//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//
//        scheduler.schedule(() -> {
//            System.out.println("✅ 1분 후 자동 큐레이션 생성 실행");
//            curationService.generateMonthlyCurationForAllUsers();
//        }, 1, TimeUnit.MINUTES);
//    }
//}