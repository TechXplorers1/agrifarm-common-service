package com.agrifarms.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_categories")
public class VehicleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public VehicleCategory() {}

    public VehicleCategory(String name) {
        this.name = name;
    }

    public VehicleCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
