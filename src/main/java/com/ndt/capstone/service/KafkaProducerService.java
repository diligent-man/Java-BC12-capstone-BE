package com.ndt.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendRegistrationEmailEvent(String email){
        String topicName = "user_registration_email";
        kafkaTemplate.send(topicName,email); //gui message bao gom topic va noi dung len broker
        System.out.println("Producer da gui message yeu cau gui mail cho " + email + "vao kafka");
    }

}
