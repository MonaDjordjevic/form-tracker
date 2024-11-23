package com.example.form_tracker.service;

import com.example.form_tracker.model.Field;
import com.example.form_tracker.model.Form;

import java.util.List;

public interface FieldService {
    List<Field> getAllFields();

    Field createField(Form form, Field field);

    Field getFieldById(Integer id);

    void deleteField(Integer id);

    Field updateField(Integer id, Field field);

    List<Field> findByFormId(Integer id);
}
