package dev.atken.springkafkadynamicconsumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerContainerManager {
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory;
    private final KafkaProperties kafkaProperties;

    @SneakyThrows
    public KafkaListenerEndpoint createKafkaListenerEndpoint(String listenerId, String topic) {
        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();
        kafkaListenerEndpoint.setId(listenerId);
        kafkaListenerEndpoint.setGroupId(kafkaProperties.getConsumer().getGroupId());
        kafkaListenerEndpoint.setAutoStartup(true);
        kafkaListenerEndpoint.setTopics(topic);
        kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        kafkaListenerEndpoint.setBean(new StringMessageListener());
        kafkaListenerEndpoint.setMethod(StringMessageListener.class.getMethod("onMessage", ConsumerRecord.class, Acknowledgment.class));
        return kafkaListenerEndpoint;
    }

    @SneakyThrows
    public void registerListener(String listenerId, String topic, boolean startImmediately) {
        kafkaListenerEndpointRegistry.registerListenerContainer(
                createKafkaListenerEndpoint(listenerId, topic), kafkaListenerContainerFactory, startImmediately
        );
    }

    public Collection<MessageListenerContainer> listContainers() {
        return kafkaListenerEndpointRegistry.getListenerContainers();
    }

    public Optional<MessageListenerContainer> getContainer(String listenerId) {
        return Optional.ofNullable(kafkaListenerEndpointRegistry.getListenerContainer(listenerId));
    }

    public void unregisterListener(String listenerId) {
        kafkaListenerEndpointRegistry.unregisterListenerContainer(listenerId);
    }
}
