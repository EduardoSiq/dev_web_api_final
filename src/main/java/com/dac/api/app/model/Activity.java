package com.dac.api.app.model;

import com.dac.api.app.enums.ActivityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private ActivityType type;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @NotNull
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @NotNull
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;
    @NotNull
    private boolean mailSent = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "space_id")
    private Space space;
    @ManyToOne()
    @JoinColumn(name = "edition_id")
    private Edition edition;
    @ManyToMany(mappedBy = "favoritedActivities")
    private List<User> favoritedByUsers;
}
