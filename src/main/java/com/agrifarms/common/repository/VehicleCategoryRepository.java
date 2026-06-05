package com.agrifarms.common.repository;

import com.agrifarms.common.entity.VehicleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleCategoryRepository extends JpaRepository<VehicleCategory, Long> {
    Optional<VehicleCategory> findByName(String name);
}
