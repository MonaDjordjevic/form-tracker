package com.example.form_tracker.rest;

import com.example.form_tracker.converter.impl.FilledFormConverter;
import com.example.form_tracker.rest.dto.FilledFormDto;
import com.example.form_tracker.service.FilledFormService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/filled-forms")
@SecurityRequirement(name = "Authorization")
public class FilledFormController {

    private final FilledFormService filledFormService;
    private final FilledFormConverter filledFormConverter;

    @PostMapping("/form/{formId}")
    @ResponseStatus(CREATED)
    public FilledFormDto createFilledForm(@PathVariable Integer formId, @Valid @RequestBody FilledFormDto filledFormDto) {
        return filledFormConverter.toDto(filledFormService.createFilledForm(formId, filledFormDto));
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public FilledFormDto getFilledFormById(@PathVariable Integer id) {
        return filledFormConverter.toDto(filledFormService.getFilledFormById(id));
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<FilledFormDto> getAllFilledForms(@ParameterObject @PageableDefault(size = 100) Pageable pageable) {
        return filledFormService.getAllFilledForms(pageable)
                .stream()
                .map(filledFormConverter::toDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteFilledForm(@PathVariable Integer id) {
        filledFormService.deleteFilledForm(id);
    }
}