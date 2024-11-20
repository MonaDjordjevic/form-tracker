package com.example.form_tracker.rest;

import com.example.form_tracker.converter.impl.FilledFieldConverter;
import com.example.form_tracker.rest.dto.FilledFieldDto;
import com.example.form_tracker.rest.dto.UpdateFilledFieldDto;
import com.example.form_tracker.service.FilledFieldService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/filled-fields")
public class FilledFieldController {

    private final FilledFieldService filledFieldService;
    private final FilledFieldConverter filledFieldConverter;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public FilledFieldDto getFilledFieldById(@PathVariable Integer id) {
        return filledFieldConverter.toDto(filledFieldService.getFilledFieldById(id));
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<FilledFieldDto> getAllFilledFields() {
        return filledFieldService.getAllFilledFields()
                .stream()
                .map(filledFieldConverter::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public FilledFieldDto updateFilledField(@PathVariable Integer id, @RequestBody UpdateFilledFieldDto updateFilledFieldDto) {
        return filledFieldConverter.toDto(filledFieldService.updateFilledField(id, updateFilledFieldDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteFilledField(@PathVariable Integer id) {
        filledFieldService.deleteFilledField(id);
    }
}
