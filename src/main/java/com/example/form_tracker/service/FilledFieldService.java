package com.example.form_tracker.service;

import com.example.form_tracker.model.Field;
import com.example.form_tracker.model.FilledField;
import com.example.form_tracker.model.FilledForm;
import com.example.form_tracker.rest.dto.FilledFieldDto;
import com.example.form_tracker.rest.dto.UpdateFilledFieldDto;

import java.util.List;
import java.util.Map;

public interface FilledFieldService {

    FilledField getFilledFieldById(Integer id);

    List<FilledField> getAllFilledFields();

    FilledField saveFilledField(Field field, FilledFieldDto filledFieldDto, FilledForm filledForm);

    FilledField updateFilledField(Integer id, UpdateFilledFieldDto updateFilledFieldDto);

    void deleteFilledField(Integer id);

    List<FilledField> findByFilledFormId(Integer id);

    Map<FilledFieldDto, Field> validateAndRetrieveFields(List<FilledFieldDto> filledFieldDtos, Integer formId);
}
