package com.poc.myapp.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.record.Record;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic.name:student}")
    private String topicName;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(topicName, message);
    }

    public void sendMessageAsync(String topic, String key, String message) {
        CompletableFuture<SendResult<String, String>> response
                = kafkaTemplate.send(new ProducerRecord<>(topic, key, message));
        try {
            response.get();
        } catch (InterruptedException | ExecutionException ie) {
            //log and retry
        }
    }
}
