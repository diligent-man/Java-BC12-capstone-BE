package com.ndt.capstone.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendRegistrationEmailEvent(String email) {
        String topicName = "user_registration_email";
        kafkaTemplate.send(topicName, email); // gui message bao gom topic va noi dung len broker
        log.info("Producer da gui message yeu cau gui mail cho " + email + " vao kafka");
    }


    public void send(String topic, String payload) {
        kafkaTemplate.send(topic, payload);
        log.info("[Outbox] Đã gửi message lên topic: " + topic);
    }
}
