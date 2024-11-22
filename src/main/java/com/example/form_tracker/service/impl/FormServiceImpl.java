package com.example.form_tracker.service.impl;

import com.example.form_tracker.model.Form;
import com.example.form_tracker.repository.FormRepository;
import com.example.form_tracker.security.CurrentUserUtil;
import com.example.form_tracker.service.FormService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final CurrentUserUtil currentUserUtil;

    @Override
    public Form getFormById(Integer id) {
        return formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Form not found with id: %s", id)));
    }

    @Override
    public Form createForm(Form form) {
        var now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        var userId = currentUserUtil.getCurrentUserId();
        form.setCreatedAt(now);
        form.setUpdatedAt(now);
        form.setCreatedBy(userId);
        form.setLastUpdatedBy(userId);
        return formRepository.save(form);
    }

    @Override
    public List<Form> getAllForms(Pageable pageable) {
        return formRepository.findAll(pageable).getContent();
    }

    @Override
    public Form updateForm(Integer id, Form form) {
        var existingForm = getFormById(id);
        var userId = currentUserUtil.getCurrentUserId();
        existingForm.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Optional.ofNullable(form.getName()).ifPresent(existingForm::setName);
        form.setLastUpdatedBy(userId);
        return formRepository.save(existingForm);
    }

    @Override
    public void deleteForm(Integer id) {
        var form = getFormById(id);
        formRepository.delete(form);
    }
}
