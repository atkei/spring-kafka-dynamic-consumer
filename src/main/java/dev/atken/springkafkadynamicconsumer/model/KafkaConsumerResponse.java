package dev.atken.springkafkadynamicconsumer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaConsumerResponse {
    private String listenerId;
    private String groupId;
    private Boolean active;
    private List<KafkaConsumerAssignment> assignments;
}
