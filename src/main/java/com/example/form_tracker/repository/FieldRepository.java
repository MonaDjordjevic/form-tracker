package com.example.form_tracker.repository;

import com.example.form_tracker.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field, Integer> {

    Optional<Field> findByDisplayOrderAndFormId(Integer displayOrder, Integer formId);
}
