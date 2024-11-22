package com.example.form_tracker.rest.dto;

import com.example.form_tracker.model.FieldType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;


import java.time.LocalDateTime;

@Data
@Builder
public class FilledFieldDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer filledFormId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String formName;

    private Integer fieldId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fieldName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer displayOrder;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private FieldType type;

    @Schema(description = "Enter text value if the field type is TEXT. Leave empty if the field type is NUMBER.")
    private String textValue;

    @Schema(description = "Enter number value if the field type is NUMBER. Leave empty if the field type is TEXT.")
    private Double numberValue;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(type = "string", pattern = "dd.MM.yyyy HH:mm", example = "20.11.2024 00:00")
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(type = "string", pattern = "dd.MM.yyyy HH:mm", example = "20.11.2024 00:00")
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer lastUpdatedBy;
}
