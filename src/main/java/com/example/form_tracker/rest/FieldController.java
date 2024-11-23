package com.example.form_tracker.rest;

import com.example.form_tracker.converter.impl.FieldConverter;
import com.example.form_tracker.rest.dto.FieldDto;
import com.example.form_tracker.service.FieldService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fields")
@SecurityRequirement(name = "Authorization")
public class FieldController {

    private final FieldService fieldService;
    private final FieldConverter fieldConverter;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public FieldDto getFieldById(@PathVariable Integer id) {
        return fieldConverter.toDto(fieldService.getFieldById(id));
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<FieldDto> getAllFields() {
        return fieldService.getAllFields()
                .stream()
                .map(fieldConverter::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public FieldDto updateField(@PathVariable Integer id, @RequestBody FieldDto fieldDto) {
        var field = fieldConverter.toEntity(fieldDto);
        return fieldConverter.toDto(fieldService.updateField(id, field));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteField(@PathVariable Integer id) {
        fieldService.deleteField(id);
    }
}

