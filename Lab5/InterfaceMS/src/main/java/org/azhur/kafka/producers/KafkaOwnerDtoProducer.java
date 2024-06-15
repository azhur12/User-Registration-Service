package org.azhur.kafka.producers;

import lombok.extern.slf4j.Slf4j;
import org.azhur.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaOwnerDtoProducer {
    private final KafkaTemplate<String, OwnerDto> kafkaTemplateOwnerDto;
    @Autowired
    public KafkaOwnerDtoProducer(KafkaTemplate<String, OwnerDto> kafkaTemplateOwnerDto) {
        this.kafkaTemplateOwnerDto = kafkaTemplateOwnerDto;
    }

    public void sendMessage(String topic, OwnerDto ownerDto) {
        log.info("***************************************");
        log.info("** KafkaOwnerDtoProducer: Sending Owner Information to Topic **");
        log.info("***************************************");
        log.info("Topic: {}", topic);
        log.info("Owner Details:");
        log.info("Name: {}", ownerDto.getName());
        log.info("Lastname: {}", ownerDto.getLastname());
        log.info("Birthday: {}", ownerDto.getBirthday());
        log.info("Passport: {}", ownerDto.getPassportNumber());
        log.info("---------------------------------------");
        kafkaTemplateOwnerDto.send(topic, ownerDto);
        log.info("Owner information sent successfully to topic: {}", topic);
    }

}
