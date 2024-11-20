package com.example.form_tracker.service;

import com.example.form_tracker.model.FilledForm;
import com.example.form_tracker.rest.dto.FilledFormDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilledFormService {
    FilledForm getFilledFormById(Integer id);

    List<FilledForm> getAllFilledForms(Pageable pageable);

    void deleteFilledForm(Integer id);

    FilledForm createFilledForm(Integer formId, FilledFormDto filledFormDto);
}