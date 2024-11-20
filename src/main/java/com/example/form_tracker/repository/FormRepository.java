package com.example.form_tracker.repository;

import com.example.form_tracker.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Integer> {
}