package com.saied.elearning.messaging;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saied.elearning.report.ReportDto;
import com.saied.elearning.report.ReportService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageReceiverService {

    private final AmazonSQS sqsClient;
    private final String queueUrl;
    private final ReportService reportService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MessageReceiverService(ReportService reportService) {
        this.reportService = reportService;
        sqsClient = AmazonSQSClientBuilder
            .standard()
            .withRegion(Regions.EU_WEST_2)
            .build();
        queueUrl = sqsClient.getQueueUrl("elearning-queue").getQueueUrl();
    }

    @Scheduled(fixedDelay = 3000)
    public void pollMessages() {
        log.info("Polling messages...");
        List<Message> messages = receiveMessages();
        for (Message message : messages) {
            ReportDto reportDto = null;
            try {
                reportDto = objectMapper.readValue(message.getBody(), ReportDto.class);
                log.info(
                    "Received message: reportDto with action: {} and timestamp {}",
                    reportDto.action(),
                    reportDto.timestamp()
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            reportService.saveReport(reportDto.action(), reportDto.timestamp());
            deleteMessage(message);
        }
    }

    private List<Message> receiveMessages() {
        ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl)
            .withMaxNumberOfMessages(10)
            .withWaitTimeSeconds(5);
        return sqsClient.receiveMessage(request).getMessages();
    }

    private void deleteMessage(Message message) {
        log.info("Removing message from queue: {}", message.getBody());
        String messageReceiptHandle = message.getReceiptHandle();
        sqsClient.deleteMessage(new DeleteMessageRequest(queueUrl, messageReceiptHandle));
    }
}
