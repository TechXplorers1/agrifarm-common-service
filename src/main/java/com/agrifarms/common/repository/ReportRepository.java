package com.agrifarms.common.repository;

import com.agrifarms.common.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findByReportedProviderId(String reportedProviderId);

    List<Report> findByReportedItemId(String reportedItemId);

    List<Report> findByReporterUserId(String reporterUserId);

    List<Report> findByStatus(String status);

    void deleteByReporterUserId(String reporterUserId);
}
