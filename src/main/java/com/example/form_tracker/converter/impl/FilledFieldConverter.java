package com.example.form_tracker.converter.impl;

import com.example.form_tracker.converter.DtoEntityConverter;
import com.example.form_tracker.model.FilledField;
import com.example.form_tracker.rest.dto.FilledFieldDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class FilledFieldConverter implements DtoEntityConverter<FilledFieldDto, FilledField> {

    @Override
    public FilledFieldDto toDto(FilledField filledField) {
        return FilledFieldDto.builder()
                .id(filledField.getId())
                .textValue(filledField.getTextValue())
                .fieldId(filledField.getField().getId())
                .fieldName(filledField.getField().getName())
                .displayOrder(filledField.getField().getDisplayOrder())
                .type(filledField.getField().getType())
                .filledFormId(filledField.getFilledForm().getId())
                .formName(filledField.getFilledForm().getForm().getName())
                .numberValue(filledField.getNumberValue())
                .createdAt(filledField.getCreatedAt())
                .updatedAt(filledField.getUpdatedAt())
                .build();
    }

    @Override
    public FilledField toEntity(FilledFieldDto filledFieldDto) {
        return FilledField.builder()
                .textValue(filledFieldDto.getTextValue())
                .numberValue(filledFieldDto.getNumberValue())
                .build();
    }
}
