package com.agrifarms.common.service;

import com.agrifarms.common.entity.Report;
import com.agrifarms.common.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    @Transactional(readOnly = true)
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Report> getReportById(String id) {
        return reportRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Report> getReportsByProviderId(String providerId) {
        return reportRepository.findByReportedProviderId(providerId);
    }

    @Transactional(readOnly = true)
    public List<Report> getReportsByReporterId(String reporterId) {
        return reportRepository.findByReporterUserId(reporterId);
    }

    public Report updateReportStatus(String id, String status) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found: " + id));
        report.setStatus(status);
        return reportRepository.save(report);
    }
}
