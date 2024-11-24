package com.example.form_tracker.rest;

import com.example.form_tracker.converter.impl.FieldConverter;
import com.example.form_tracker.converter.impl.FormConverter;
import com.example.form_tracker.rest.dto.FieldDto;
import com.example.form_tracker.rest.dto.FormDto;
import com.example.form_tracker.service.FormService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
@SecurityRequirement(name = "Authorization")
public class FormController {

    private final FormService formService;
    private final FormConverter formConverter;
    private final FieldConverter fieldConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    public FormDto createForm(@Valid @RequestBody FormDto formDto) {
        var form = formConverter.toEntity(formDto);
        return formConverter.toDto(formService.createForm(form));
    }

    @PostMapping("/{formId}/fields")
    @ResponseStatus(CREATED)
    public List<FieldDto> createFields(@PathVariable Integer formId, @RequestBody @NotEmpty(message = "Input field list cannot be empty.") List<@Valid FieldDto> fieldDtoList) {
        var fields = fieldDtoList.stream()
                .map(fieldConverter::toEntity)
                .toList();
        var createdFields = formService.createFieldsBatch(formId, fields);
        return createdFields.stream()
                .map(fieldConverter::toDto)
                .toList();
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
    public FormDto updateForm(@PathVariable Integer id, @Valid @RequestBody FormDto formDto) {
        var form = formConverter.toEntity(formDto);
        return formConverter.toDto(formService.updateForm(id, form));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteForm(@PathVariable Integer id) {
        formService.deleteForm(id);
    }
}