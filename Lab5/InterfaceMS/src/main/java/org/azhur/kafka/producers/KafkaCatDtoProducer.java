package org.azhur.kafka.producers;

import lombok.extern.slf4j.Slf4j;
import org.azhur.dto.CatDto;
import org.azhur.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaCatDtoProducer {
    private final KafkaTemplate<String, CatDto> kafkaTemplateCatDto;
    @Autowired
    public KafkaCatDtoProducer(KafkaTemplate<String, CatDto> kafkaTemplateCatDto) {
        this.kafkaTemplateCatDto = kafkaTemplateCatDto;
    }

    public void sendMessage(String topic, CatDto catDto) {
        log.info("***************************************");
        log.info("** KafkaCatDtoProducer: Sending Cat Information to Topic **");
        log.info("***************************************");
        log.info("Topic: {}", topic);
        log.info("Cat Details:");
        log.info("Name: {}", catDto.getName());
        log.info("BirthDay: {}", catDto.getBirthday());
        log.info("Breed: {}", catDto.getBreed());
        log.info("Color: {}", catDto.getColor());
        log.info("---------------------------------------");
        kafkaTemplateCatDto.send(topic, catDto);
        log.info("Cat information sent successfully!");
    }

}
