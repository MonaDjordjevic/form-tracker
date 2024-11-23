package com.example.form_tracker.rest.dto;

import com.example.form_tracker.model.FieldType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Data
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "fieldType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextFilledFieldDto.class, name = "TEXT"),
        @JsonSubTypes.Type(value = NumberFilledFieldDto.class, name = "NUMBER")
})
@NoArgsConstructor
@AllArgsConstructor
public abstract class FilledFieldDto {

    private Integer fieldId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String fieldName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer displayOrder;

    private FieldType fieldType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer filledFormId;

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