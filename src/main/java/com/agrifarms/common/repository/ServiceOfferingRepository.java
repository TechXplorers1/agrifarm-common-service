package com.agrifarms.common.repository;

import com.agrifarms.common.entity.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, String> {
    List<ServiceOffering> findByIsAvailableTrue();

    List<ServiceOffering> findByServiceType(String serviceType);
}
