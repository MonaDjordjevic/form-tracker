package com.example.form_tracker.rest;

import com.example.form_tracker.converter.impl.FormConverter;
import com.example.form_tracker.rest.dto.FormDto;
import com.example.form_tracker.service.FormService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/forms")
public class FormController {

    private final FormService formService;
    private final FormConverter formConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    public FormDto createForm(@Valid @RequestBody FormDto formDto) {
        var form = formConverter.toEntity(formDto);
        return formConverter.toDto(formService.createForm(form));
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public FormDto getFormById(@PathVariable Integer id) {
        return formConverter.toDto(formService.getFormById(id));
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<FormDto> getAllForms(@ParameterObject @PageableDefault(size = 100) Pageable pageable) {
        return formService.getAllForms(pageable)
                .stream()
                .map(formConverter::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public FormDto updateForm(@PathVariable Integer id, @RequestBody FormDto formDto) {
        var form = formConverter.toEntity(formDto);
        return formConverter.toDto(formService.updateForm(id, form));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteForm(@PathVariable Integer id) {
        formService.deleteForm(id);
    }
}