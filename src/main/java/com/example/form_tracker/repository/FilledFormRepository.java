package com.example.form_tracker.repository;

import com.example.form_tracker.model.FilledForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilledFormRepository extends JpaRepository<FilledForm, Integer> {
}