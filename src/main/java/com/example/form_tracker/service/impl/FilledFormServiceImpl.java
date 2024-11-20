package com.example.form_tracker.service.impl;

import com.example.form_tracker.model.FilledForm;
import com.example.form_tracker.repository.FilledFormRepository;
import com.example.form_tracker.rest.dto.FilledFormDto;
import com.example.form_tracker.service.FilledFieldService;
import com.example.form_tracker.service.FilledFormService;
import com.example.form_tracker.service.FormService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class FilledFormServiceImpl implements FilledFormService {

    private final FilledFormRepository filledFormRepository;
    private final FilledFieldService filledFieldService;
    private final FormService formService;

    @Override
    public FilledForm createFilledForm(Integer formId, FilledFormDto filledFormDto) {
        var form = formService.getFormById(formId);
        var now = LocalDateTime.now();
        var filledForm = FilledForm.builder()
                .form(form)
                .createdAt(now)
                .updatedAt(now)
                .build();

        var fieldsMap = filledFieldService.validateAndRetrieveFields(filledFormDto.getFilledFields(), formId);
        var savedFilledForm = filledFormRepository.save(filledForm);

        filledFormDto.getFilledFields().forEach(filledFieldDto ->
                filledFieldService.saveFilledField(fieldsMap.get(filledFieldDto), filledFieldDto, savedFilledForm)
        );
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
}