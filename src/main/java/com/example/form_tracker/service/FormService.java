package com.example.form_tracker.service;

import com.example.form_tracker.model.Field;
import com.example.form_tracker.model.Form;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FormService {

    Form getFormById(Integer id);

    Form createForm(Form form);

    List<Form> getAllForms(Pageable pageable);

    Form updateForm(Integer id, Form form);

    void deleteForm(Integer id);

    List<Field> createFieldsBatch(Integer formId, List<Field> fields);
}
