package com.agrifarms.common.controller;

import com.agrifarms.common.dto.ReportDTO;
import com.agrifarms.common.entity.Report;
import com.agrifarms.common.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO dto) {
        Report report = new Report();
        report.setReporterUserId(dto.getReporterUserId());
        report.setReportedItemId(dto.getReportedItemId());
        report.setReportedItemName(dto.getReportedItemName());
        report.setReportedProviderId(dto.getReportedProviderId());
        report.setReason(dto.getReason());
        report.setDetails(dto.getDetails());
        report.setBlocked(dto.getBlocked() != null ? dto.getBlocked() : false);
        report.setStatus("PENDING");
        report.setCreatedAt(LocalDateTime.now());

        Report saved = reportService.saveReport(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        List<ReportDTO> dtos = reportService.getAllReports()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ReportDTO>> getReportsForProvider(@PathVariable String providerId) {
        List<ReportDTO> dtos = reportService.getReportsByProviderId(providerId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/reporter/{reporterId}")
    public ResponseEntity<List<ReportDTO>> getReportsByReporter(@PathVariable String reporterId) {
        List<ReportDTO> dtos = reportService.getReportsByReporterId(reporterId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReportDTO> updateStatus(
            @PathVariable String id,
            @RequestParam String status) {
        Report updated = reportService.updateReportStatus(id, status);
        return ResponseEntity.ok(toDTO(updated));
    }

    private ReportDTO toDTO(Report entity) {
        ReportDTO dto = new ReportDTO();
        dto.setId(entity.getId());
        dto.setReporterUserId(entity.getReporterUserId());
        dto.setReportedItemId(entity.getReportedItemId());
        dto.setReportedItemName(entity.getReportedItemName());
        dto.setReportedProviderId(entity.getReportedProviderId());
        dto.setReason(entity.getReason());
        dto.setDetails(entity.getDetails());
        dto.setBlocked(entity.getBlocked());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setTimestamp(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);
        return dto;
    }
}
