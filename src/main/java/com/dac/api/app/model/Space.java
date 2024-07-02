package com.dac.api.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "spaces")
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private int capacity;

    @ElementCollection
    private List<String> resources;

    @OneToOne(mappedBy = "space", cascade = CascadeType.ALL)
    private Activity activity;

}
