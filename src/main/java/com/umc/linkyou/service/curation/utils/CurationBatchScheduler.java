package com.umc.linkyou.service.curation.utils;

import com.umc.linkyou.service.curation.CurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurationBatchScheduler {

    private final CurationService curationService;

    @Scheduled(cron = "0 5 0 1 * *", zone = "Asia/Seoul")
    public void runMonthlyCurationBatch() {
        curationService.generateMonthlyCurationForAllUsers();
    }
}