package com.example.form_tracker.repository;

import com.example.form_tracker.model.FilledField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilledFieldRepository extends JpaRepository<FilledField, Integer> {

    List<FilledField> findByFilledFormId(Integer formId);
}
