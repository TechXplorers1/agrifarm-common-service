package com.agrifarms.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transport_vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportVehicle {

    @Id
    @Column(name = "vehicle_id")
    @UuidGenerator
    private String vehicleId;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "vehicle_type")
    private String vehicleType; // Mini Truck, Tractor Trolley, etc.

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "load_capacity")
    private String loadCapacity;

    @Column(name = "price_per_km_or_trip")
    private BigDecimal pricePerKmOrTrip;

    @Column(name = "driver_included")
    private Boolean driverIncluded;

    @Column(name = "service_area")
    private String serviceArea;

    private String location;

    @Column(name = "is_available")
    private Boolean isAvailable;

    private BigDecimal rating;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
