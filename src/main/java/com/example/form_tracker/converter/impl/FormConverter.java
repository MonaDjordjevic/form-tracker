package com.example.form_tracker.converter.impl;

import com.example.form_tracker.converter.DtoEntityConverter;
import com.example.form_tracker.model.Field;
import com.example.form_tracker.model.Form;
import com.example.form_tracker.rest.dto.FieldBasicInfoDto;
import com.example.form_tracker.rest.dto.FormDto;
import com.example.form_tracker.service.FieldService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FormConverter implements DtoEntityConverter<FormDto, Form> {

    private final FieldService fieldService;

    @Override
    public FormDto toDto(Form form) {
        var fields = fieldService.findByFormId(form.getId())
                .stream()
                .map(this::mapToFieldInfoDto)
                .toList();

        return FormDto.builder()
                .id(form.getId())
                .name(form.getName())
                .createdAt(form.getCreatedAt())
                .updatedAt(form.getUpdatedAt())
                .createdBy(form.getCreatedBy())
                .lastUpdatedBy(form.getLastUpdatedBy())
                .fields(fields)
                .build();
    }

    @Override
    public Form toEntity(FormDto formDto) {
        return Form.builder()
                .id(formDto.getId())
                .name(formDto.getName())
                .build();
    }

    private FieldBasicInfoDto mapToFieldInfoDto(Field field) {
        return FieldBasicInfoDto.builder()
                .id(field.getId())
                .name(field.getName())
                .type(field.getType())
                .displayOrder(field.getDisplayOrder())
                .build();
    }
}