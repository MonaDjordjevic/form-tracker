package com.example.form_tracker.service.impl;

import com.example.form_tracker.model.Statistics;
import com.example.form_tracker.repository.FilledFormRepository;
import com.example.form_tracker.repository.StatisticsRepository;
import com.example.form_tracker.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final FilledFormRepository filledFormRepository;
    private final StatisticsRepository statisticsRepository;

    @Override
    public void calculateAndSaveDailyStatistics() {
        var startOfDay = LocalDate.now().minusDays(1).atStartOfDay();
        var endOfDay = startOfDay.plusDays(1);

        var filledFormsCount = filledFormRepository.countByCreatedAtBetween(startOfDay, endOfDay);
        var statistics = Statistics.builder()
                .date(startOfDay.toLocalDate())
                .filledFormsCount(filledFormsCount)
                .build();

        statisticsRepository.save(statistics);
    }
}
