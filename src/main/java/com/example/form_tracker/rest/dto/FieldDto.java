package com.example.form_tracker.rest.dto;

import com.example.form_tracker.model.FieldType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FieldDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer formId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String formName;

    @NotNull(message = "Name must not be null")
    private String name;

    @NotNull(message = "Display order must not be null")
    @Min(value = 1, message = "Display order must be greater than or equal to 1.")
    private Integer displayOrder;

    @NotNull(message = "Field type must not be null")
    private FieldType type;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(type = "string", pattern = "dd.MM.yyyy HH:mm", example = "20.11.2024 00:00")
    private LocalDateTime createdAt;

    @Schema(type = "string", pattern = "dd.MM.yyyy HH:mm", example = "20.11.2024 00:00")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
