package com.example.form_tracker.converter.impl;

import com.example.form_tracker.converter.DtoEntityConverter;
import com.example.form_tracker.model.Form;
import com.example.form_tracker.rest.dto.FormDto;
import org.springframework.stereotype.Component;

@Component
public class FormConverter implements DtoEntityConverter<FormDto, Form> {

    @Override
    public FormDto toDto(Form form) {
        return FormDto.builder()
                .id(form.getId())
                .name(form.getName())
                .createdAt(form.getCreatedAt())
                .updatedAt(form.getUpdatedAt())
                .createdBy(form.getCreatedBy())
                .lastUpdatedBy(form.getLastUpdatedBy())
                .build();
    }

    @Override
    public Form toEntity(FormDto formDto) {
        return Form.builder()
                .id(formDto.getId())
                .name(formDto.getName())
                .build();
    }
}