package org.azhur.kafka.producers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaIntegerProducer {
    private final KafkaTemplate<String, Integer> kafkaTemplateInteger;
    @Autowired
    public KafkaIntegerProducer(KafkaTemplate<String, Integer> kafkaTemplate) {
        this.kafkaTemplateInteger = kafkaTemplate;
    }

    public void sendMessage(String topic, Integer value) {
        log.info("***************************************");
        log.info("** KafkaIntegerProducer: Sending Owner ID to Topic **");
        log.info("***************************************");
        log.info("Topic: {}", topic);
        log.info("Owner ID: {}", value);
        log.info("---------------------------------------");
        kafkaTemplateInteger.send(topic, value);
        log.info("Owner ID sent successfully to topic: {}", topic);
    }

}
