package com.example.form_tracker.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UpdateFilledFieldDto {

    @Schema(description = "Enter text value if the field type is TEXT. Leave empty if the field type is NUMBER.")
    private String textValue;

    @Schema(description = "Enter number value if the field type is NUMBER. Leave empty if the field type is TEXT.")
    private Double numberValue;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(type = "string", pattern = "dd.MM.yyyy HH:mm", example = "20.11.2024 00:00")
    private LocalDateTime updatedAt;
}

