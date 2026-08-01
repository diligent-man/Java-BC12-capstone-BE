package com.ndt.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendRegistrationEmailEvent(String email) {
        String topicName = "user-registration-topic";
        kafkaTemplate.send(topicName, email);
        System.out.println("[Producer] Đã ném yêu cầu gửi mail cho: " + email + " vào Kafka");
    }
}
