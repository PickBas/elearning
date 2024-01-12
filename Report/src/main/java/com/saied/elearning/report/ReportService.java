package com.saied.elearning.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report saveReport(String action, String timestamp) {
        log.info("Saving report with action: {} and timestamp: {}", action, timestamp);
        Report report = Report.builder()
                .action(action)
                .timeStamp(timestamp)
                .build();
        return reportRepository.save(report);
    }

    public Report getReport(String id) {
        log.info("Getting report with id: {}", id);
        return reportRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Report with id " + id + " was not found")
            );
    }
}
