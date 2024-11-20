package com.example.form_tracker.converter.impl;

import com.example.form_tracker.converter.DtoEntityConverter;
import com.example.form_tracker.model.FilledForm;
import com.example.form_tracker.rest.dto.FilledFormDto;
import com.example.form_tracker.service.FilledFieldService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FilledFormConverter implements DtoEntityConverter<FilledFormDto, FilledForm> {

    private final FilledFieldService filledFieldService;
    private final FilledFieldConverter filledFieldConverter;

    @Override
    public FilledFormDto toDto(FilledForm filledForm) {
        var filledFieldDtos = filledFieldService.findByFilledFormId(filledForm.getId())
                .stream()
                .map(filledFieldConverter::toDto)
                .toList();
        return FilledFormDto.builder()
                .id(filledForm.getId())
                .updatedAt(filledForm.getUpdatedAt())
                .createdAt(filledForm.getCreatedAt())
                .formId(filledForm.getForm().getId())
                .formName(filledForm.getForm().getName())
                .filledFields(filledFieldDtos)
                .build();
    }

    @Override
    public FilledForm toEntity(FilledFormDto filledFormDto) {
        return FilledForm.builder()
                .build();
    }
}