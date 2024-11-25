package com.example.form_tracker.service.impl;

import com.example.form_tracker.model.Field;
import com.example.form_tracker.model.FieldType;
import com.example.form_tracker.model.FilledForm;
import com.example.form_tracker.repository.FilledFormRepository;
import com.example.form_tracker.rest.dto.FilledFieldDto;
import com.example.form_tracker.rest.dto.FilledFormDto;
import com.example.form_tracker.rest.dto.NumberFilledFieldDto;
import com.example.form_tracker.rest.dto.TextFilledFieldDto;
import com.example.form_tracker.security.CurrentUserUtil;
import com.example.form_tracker.service.FilledFieldService;
import com.example.form_tracker.service.FilledFormService;
import com.example.form_tracker.service.FormService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class FilledFormServiceImpl implements FilledFormService {

    private final FilledFormRepository filledFormRepository;
    private final FilledFieldService filledFieldService;
    private final FormService formService;
    private final CurrentUserUtil currentUserUtil;

    @Override
    public FilledForm createFilledForm(Integer formId, FilledFormDto filledFormDto) {
        var userId = currentUserUtil.getCurrentUserId();
        var form = formService.getFormById(formId);
        var now = LocalDateTime.now();
        var filledForm = FilledForm.builder()
                .form(form)
                .createdAt(now)
                .updatedAt(now)
                .createdBy(userId)
                .lastUpdatedBy(userId)
                .build();

        var fieldsMap = filledFieldService.validateAndRetrieveFields(filledFormDto.getFilledFields(), formId);
        var savedFilledForm = filledFormRepository.save(filledForm);

        filledFormDto.getFilledFields().forEach(filledFieldDto -> {
            validateFieldTypeAndValue(fieldsMap.get(filledFieldDto), filledFieldDto);
            filledFieldService.saveFilledField(fieldsMap.get(filledFieldDto), filledFieldDto, savedFilledForm);
        });
        return savedFilledForm;
    }

    @Override
    public FilledForm getFilledFormById(Integer id) {
        return filledFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("FilledForm with id %s not found", id)));
    }

    @Override
    public List<FilledForm> getAllFilledForms(Pageable pageable) {
        return filledFormRepository.findAll(pageable).getContent();
    }

    @Override
    public void deleteFilledForm(Integer id) {
        var filledForm = getFilledFormById(id);
        filledFormRepository.delete(filledForm);
    }

    private void validateFieldTypeAndValue(Field field, FilledFieldDto filledFieldDto) {
        var expectedType = field.getType();
        if (expectedType == FieldType.NUMBER) {
            validateNumberField(filledFieldDto, field);
        } else if (expectedType == FieldType.TEXT) {
            validateTextField(filledFieldDto, field);
        } else {
            throw new IllegalArgumentException(format("Unsupported field type: %s", expectedType));
        }
    }

    private void validateNumberField(FilledFieldDto filledFieldDto, Field field) {
        if (!(filledFieldDto instanceof NumberFilledFieldDto numberField)) {
            throw new IllegalArgumentException(format("For field with id %s you must provide a value for type: NUMBER", field.getId()));
        }
        Assert.notNull(numberField.getNumberValue(), "Number value must not be null for a field of type NUMBER.");
    }

    private void validateTextField(FilledFieldDto filledFieldDto, Field field) {
        if (!(filledFieldDto instanceof TextFilledFieldDto textField)) {
            throw new IllegalArgumentException(format("For field with id %s you must provide a value for type: TEXT", field.getId()));
        }
        Assert.hasText(textField.getTextValue(), "Text value must not be null or empty for a field of type TEXT.");
    }
}