package com.dac.api.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShowResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String acronym;
    private String url;

    private List<EditionResponseDTO> editions;
}
