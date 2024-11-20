package com.example.form_tracker.service.impl;

import com.example.form_tracker.model.Field;
import com.example.form_tracker.model.FieldType;
import com.example.form_tracker.model.FilledField;
import com.example.form_tracker.model.FilledForm;
import com.example.form_tracker.repository.FilledFieldRepository;
import com.example.form_tracker.rest.dto.FilledFieldDto;
import com.example.form_tracker.rest.dto.UpdateFilledFieldDto;
import com.example.form_tracker.service.FieldService;
import com.example.form_tracker.service.FilledFieldService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class FilledFieldServiceImpl implements FilledFieldService {

    private final FilledFieldRepository filledFieldRepository;
    private final FieldService fieldService;

    @Override
    public FilledField getFilledFieldById(Integer id) {
        return filledFieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Filled field not found for id: %s", id)));
    }

    @Override
    public List<FilledField> getAllFilledFields() {
        return filledFieldRepository.findAll();
    }

    @Override
    public FilledField saveFilledField(Field field, FilledFieldDto filledFieldDto, FilledForm filledForm) {
        var filledField = FilledField.builder()
                .field(field)
                .filledForm(filledForm)
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .updatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        validateAndAssignValues(filledFieldDto.getNumberValue(), filledFieldDto.getTextValue(), filledField);
        return filledFieldRepository.save(filledField);
    }

    @Override
    public FilledField updateFilledField(Integer id, UpdateFilledFieldDto updateFilledFieldDto) {
        var filledFieldToUpdate = getFilledFieldById(id);
        validateAndAssignValues(updateFilledFieldDto.getNumberValue(), updateFilledFieldDto.getTextValue(), filledFieldToUpdate);

        filledFieldToUpdate.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return filledFieldRepository.save(filledFieldToUpdate);
    }

    @Override
    public void deleteFilledField(Integer id) {
        var filledField = getFilledFieldById(id);
        filledFieldRepository.delete(filledField);
    }

    @Override
    public List<FilledField> findByFilledFormId(Integer id) {
        return filledFieldRepository.findByFilledFormId(id);
    }

    @Override
    public Map<FilledFieldDto, Field> validateAndRetrieveFields(List<FilledFieldDto> filledFieldDtos, Integer formId) {
        return filledFieldDtos.stream()
                .collect(Collectors.toMap(
                        filledFieldDto -> filledFieldDto,
                        filledFieldDto -> retrieveFieldBelongingToForm(filledFieldDto, formId),
                        (existing, duplicate) -> {
                            throw new IllegalArgumentException(String.format(
                                    "Filled field with id=%d already exists in the filled form.",
                                    existing.getId()
                            ));
                        }
                ));
    }

    private Field retrieveFieldBelongingToForm(FilledFieldDto filledFieldDto, Integer formId) {
        var field = fieldService.getFieldById(filledFieldDto.getFieldId());
        if (!field.getForm().getId().equals(formId)) {
            throw new IllegalArgumentException(format("Field with id %s does not belong to the form with id: %s.", field.getId(), formId));
        }
        return field;
    }

    private static void validateAndAssignValues(Double numberValue, String textValue, FilledField filledFieldToUpdate) {
        var fieldType = filledFieldToUpdate.getField().getType();
        if (fieldType == FieldType.TEXT) {
            Assert.notNull(textValue, "Text value must not be null for a field of type TEXT.");
            Assert.isNull(numberValue, "Cannot update number value for a field of type TEXT.");
            filledFieldToUpdate.setTextValue(textValue);
        } else if (fieldType == FieldType.NUMBER) {
            Assert.notNull(numberValue, "Number value must not be null for a field of type NUMBER.");
            Assert.isNull(textValue, "Cannot update text value for a field of type NUMBER.");
            filledFieldToUpdate.setNumberValue(numberValue);
        }
    }

}