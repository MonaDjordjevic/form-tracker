package com.example.form_tracker.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class FormDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotBlank(message = "Name must not be blank.")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(type = "string", pattern = "dd.MM.yyyy HH:mm", example = "20.11.2024 00:00")
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(type = "string", pattern = "dd.MM.yyyy HH:mm", example = "20.11.2024 00:00")
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<FieldBasicInfoDto> fields;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer lastUpdatedBy;
}