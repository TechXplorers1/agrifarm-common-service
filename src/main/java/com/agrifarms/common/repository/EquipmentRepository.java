package com.agrifarms.common.repository;

import com.agrifarms.common.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    List<Equipment> findByIsAvailableTrue();

    List<Equipment> findByCategory(String category);

    List<Equipment> findByOwnerId(String ownerId);
}
