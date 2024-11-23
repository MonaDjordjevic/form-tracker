package com.example.form_tracker.rest.dto;

import com.example.form_tracker.model.FieldType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldBasicInfoDto {

    private Integer id;
    private String name;
    private FieldType type;
    private Integer displayOrder;
}