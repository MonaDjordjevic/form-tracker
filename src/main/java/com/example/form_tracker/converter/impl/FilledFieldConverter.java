package com.example.form_tracker.converter.impl;

import com.example.form_tracker.converter.DtoEntityConverter;
import com.example.form_tracker.model.FieldType;
import com.example.form_tracker.model.FilledField;
import com.example.form_tracker.rest.dto.FilledFieldDto;
import com.example.form_tracker.rest.dto.NumberFilledFieldDto;
import com.example.form_tracker.rest.dto.TextFilledFieldDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class FilledFieldConverter implements DtoEntityConverter<FilledFieldDto, FilledField> {

    @Override
    public FilledFieldDto toDto(FilledField filledField) {
        if (filledField.getField().getType() == FieldType.TEXT) {
            return TextFilledFieldDto.builder()
                    .id(filledField.getId())
                    .textValue(filledField.getTextValue())
                    .fieldName(filledField.getField().getName())
                    .fieldId(filledField.getField().getId())
                    .filledFormId(filledField.getFilledForm().getId())
                    .fieldType(FieldType.TEXT)
                    .displayOrder(filledField.getField().getDisplayOrder())
                    .createdAt(filledField.getCreatedAt())
                    .updatedAt(filledField.getUpdatedAt())
                    .createdAt(filledField.getCreatedAt())
                    .createdBy(filledField.getCreatedBy())
                    .lastUpdatedBy(filledField.getLastUpdatedBy())
                    .build();
        } else {
            return NumberFilledFieldDto.builder()
                    .id(filledField.getId())
                    .numberValue(filledField.getNumberValue())
                    .fieldName(filledField.getField().getName())
                    .fieldId(filledField.getField().getId())
                    .filledFormId(filledField.getFilledForm().getId())
                    .fieldType(FieldType.NUMBER)
                    .displayOrder(filledField.getField().getDisplayOrder())
                    .createdAt(filledField.getCreatedAt())
                    .updatedAt(filledField.getUpdatedAt())
                    .createdAt(filledField.getCreatedAt())
                    .createdBy(filledField.getCreatedBy())
                    .lastUpdatedBy(filledField.getLastUpdatedBy())
                    .build();
        }
    }

    @Override
    public FilledField toEntity(FilledFieldDto filledFieldDto) {
        if (filledFieldDto instanceof TextFilledFieldDto) {
            return FilledField.builder().textValue(((TextFilledFieldDto) filledFieldDto).getTextValue()).build();
        } else {
            return FilledField.builder().numberValue(((NumberFilledFieldDto) filledFieldDto).getNumberValue()).build();
        }
    }
}
