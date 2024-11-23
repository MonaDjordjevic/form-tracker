package com.example.form_tracker.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NumberFilledFieldDto extends FilledFieldDto {

    private Double numberValue;
}