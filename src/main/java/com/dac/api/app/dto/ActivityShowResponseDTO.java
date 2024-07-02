package com.dac.api.app.dto;

import com.dac.api.app.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityShowResponseDTO {
    private Long id;
    private ActivityType type;
    private String name;
    private String description;
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private EditionResponseDTO edition;
    private SpaceResponseDTO space;
}
