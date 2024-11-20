package com.example.form_tracker.service.impl;

import com.example.form_tracker.model.Field;
import com.example.form_tracker.repository.FieldRepository;
import com.example.form_tracker.service.FieldService;
import com.example.form_tracker.service.FormService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FormService formService;

    @Override
    public Field createField(Integer formId, Field field) {
        var form = formService.getFormById(formId);
        validateUniqueDisplayOrder(formId, field);
        var now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        field.setForm(form);
        field.setCreatedAt(now);
        field.setUpdatedAt(now);

        return fieldRepository.save(field);
    }

    @Override
    public Field getFieldById(Integer id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Field does not exist with id: %s.", id)));
    }

    @Override
    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    @Override
    public Field updateField(Integer id, Field field) {
        var existingField = getFieldById(id);
        validateUniqueDisplayOrder(existingField.getForm().getId(), field);
        applyNonNullProperties(field, existingField);
        existingField.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return fieldRepository.save(existingField);
    }

    @Override
    public void deleteField(Integer id) {
        var field = getFieldById(id);
        fieldRepository.delete(field);
    }

    private void validateUniqueDisplayOrder(Integer formId, Field field) {
        fieldRepository.findByDisplayOrderAndFormId(field.getDisplayOrder(), formId)
                .ifPresent(existingField -> {
                    throw new IllegalArgumentException(String.format("Field with display order %d already exists in form with id %d.",
                            field.getDisplayOrder(), formId));
                });
    }

    private void applyNonNullProperties(Field source, Field target) {
        Optional.ofNullable(source.getType()).ifPresent(target::setType);
        Optional.ofNullable(source.getName()).ifPresent(target::setName);
        Optional.ofNullable(source.getDisplayOrder()).ifPresent(target::setDisplayOrder);
    }
}
