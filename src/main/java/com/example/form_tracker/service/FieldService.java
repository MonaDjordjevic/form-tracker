package com.example.form_tracker.service;

import com.example.form_tracker.model.Field;

import java.util.List;

public interface FieldService {
    List<Field> getAllFields();

    Field getFieldById(Integer id);

    Field createField(Integer formId, Field field);

    void deleteField(Integer id);

    Field updateField(Integer id, Field field);
}
