package com.poc.myapp.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class StudentProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic.name:student}")
    private String topicName;

    public StudentProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(topicName, message);
    }

    public void sendMessageBlocking(String topic, String key, String message) {
        CompletableFuture<SendResult<String, String>> response
                = kafkaTemplate.send(new ProducerRecord<>(topic, key, message));
        try {
            response.get();
        } catch (InterruptedException | ExecutionException ie) {
            //log and retry
        }
    }

    public void sendMessageAsync(String topic, String key, String message) {
        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topic, key, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
}
}
