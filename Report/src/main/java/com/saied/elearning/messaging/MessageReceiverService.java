package com.saied.elearning.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saied.elearning.report.ReportDto;
import com.saied.elearning.report.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiverService {

    private final ReportService reportService;

    public MessageReceiverService(ReportService reportService) {
        this.reportService = reportService;
    }

    @JmsListener(destination = "report-queue")
    public void receiveMessage(String json) {
        log.info("Received message: {}", json);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ReportDto reportDto = objectMapper.readValue(json, ReportDto.class);
            this.reportService.saveReport(reportDto.action(), reportDto.timestamp());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
