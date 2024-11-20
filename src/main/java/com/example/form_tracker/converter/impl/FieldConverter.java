package com.example.form_tracker.converter.impl;

import com.example.form_tracker.converter.DtoEntityConverter;
import com.example.form_tracker.model.Field;
import com.example.form_tracker.rest.dto.FieldDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FieldConverter implements DtoEntityConverter<FieldDto, Field> {

    @Override
    public FieldDto toDto(Field field) {
        return FieldDto
                .builder()
                .type(field.getType())
                .name(field.getName())
                .id(field.getId())
                .formId(field.getForm().getId())
                .formName(field.getForm().getName())
                .createdAt(field.getCreatedAt())
                .displayOrder(field.getDisplayOrder())
                .updatedAt(field.getUpdatedAt())
                .build();
    }

    @Override
    public Field toEntity(FieldDto fieldDto) {
        return Field
                .builder()
                .type(fieldDto.getType())
                .name(fieldDto.getName())
                .id(fieldDto.getId())
                .displayOrder(fieldDto.getDisplayOrder())
                .build();
    }
}
