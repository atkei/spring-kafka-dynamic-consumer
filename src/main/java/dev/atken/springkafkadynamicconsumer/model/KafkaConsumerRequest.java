package dev.atken.springkafkadynamicconsumer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaConsumerRequest {
    private String topic;
    private boolean startImmediately = true;
}
