package dev.atken.springkafkadynamicconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringKafkaDynamicConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaDynamicConsumerApplication.class, args);
	}

}
