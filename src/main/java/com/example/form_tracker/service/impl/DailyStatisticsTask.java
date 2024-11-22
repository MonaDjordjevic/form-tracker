package com.example.form_tracker.service.impl;

import com.example.form_tracker.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyStatisticsTask {

    private final StatisticsService statisticsService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void runDailyStatisticsTask() {
        statisticsService.calculateAndSaveDailyStatistics();
    }
}
