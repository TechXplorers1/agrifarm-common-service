package com.agrifarms.common.repository;

import com.agrifarms.common.entity.TransportVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportVehicleRepository extends JpaRepository<TransportVehicle, String> {
    List<TransportVehicle> findByIsAvailableTrue();

    List<TransportVehicle> findByVehicleType(String vehicleType);
}
