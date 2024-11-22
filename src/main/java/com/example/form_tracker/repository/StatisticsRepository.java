package com.example.form_tracker.repository;

import com.example.form_tracker.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
}