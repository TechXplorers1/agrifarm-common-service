package com.agrifarms.common.repository;

import com.agrifarms.common.entity.WorkerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerGroupRepository extends JpaRepository<WorkerGroup, String> {
    List<WorkerGroup> findByLocationContainingIgnoreCase(String location);

    List<WorkerGroup> findByOwnerId(String ownerId);
}
