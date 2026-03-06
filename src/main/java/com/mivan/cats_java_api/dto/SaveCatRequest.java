package com.mivan.cats_java_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SaveCatRequest {

    @NotBlank
    @JsonProperty("cat_id")
    private String catId;

    @NotBlank
    private String url;

    @NotNull
    @Positive
    private Integer width;

    @NotNull
    @Positive
    private Integer height;

    @NotNull
    private List<Map<String, Object>> breeds;
}