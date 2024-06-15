package org.azhur.kafka.producers;

import lombok.extern.slf4j.Slf4j;
import org.azhur.dto.MyUserDto;
import org.azhur.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMyUserDtoProducer {
    private final KafkaTemplate<String, MyUserDto> kafkaTemplateMyUserDto;
    @Autowired
    public KafkaMyUserDtoProducer(KafkaTemplate<String, MyUserDto> kafkaTemplateOwnerDto) {
        this.kafkaTemplateMyUserDto = kafkaTemplateOwnerDto;
    }

    public void sendMessage(String topic, MyUserDto myUserDto) {
        log.info("***************************************");
        log.info("** KafkaMyUserDtoProducer: Sending User Information to Topic **");
        log.info("***************************************");
        log.info("Topic: {}", topic);
        log.info("User Details:");
        log.info("OwnerId: {}", myUserDto.getOwnerDto().getName());
        log.info("Email: {}", myUserDto.getEmail());
        log.info("BirthDay: {}", myUserDto.getOwnerDto().getBirthday());
        log.info("Role: {}", myUserDto.getRole());
        log.info("---------------------------------------");
        kafkaTemplateMyUserDto.send(topic, myUserDto);
        log.info("User information sent successfully to topic: {}", topic);
    }

}
