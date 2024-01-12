package com.saied.elearning.mainmicroservice.messaging;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AwsSQSService {

    private final AmazonSQS sqsClient;
    private final String queueUrl;

    public AwsSQSService() {
        this.sqsClient = AmazonSQSClientBuilder
            .standard()
            .withRegion(Regions.EU_WEST_2)
            .build();
        this.queueUrl = sqsClient.getQueueUrl("elearning-queue").getQueueUrl();
    }

    public void send(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(object);
            send(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message) {
        log.info("Sending message: {}; to queue: {}", message, queueUrl);
        SendMessageRequest send_msg_request = new SendMessageRequest()
            .withQueueUrl(queueUrl)
            .withMessageBody(message);
        sqsClient.sendMessage(send_msg_request);
    }
}
