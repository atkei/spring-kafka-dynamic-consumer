package dev.atken.springkafkadynamicconsumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringMessageListener implements AcknowledgingMessageListener<String, String> {
    @Override
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
        try {
            log.info("Received new message: topic={}, value={}", record.topic(), record.value());
            ack.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
