package com.saied.elearning.acmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderService {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public MessageSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendMessage(String destination, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            jmsTemplate.convertAndSend(destination, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
