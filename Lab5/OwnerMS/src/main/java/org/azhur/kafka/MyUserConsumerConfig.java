package org.azhur.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.azhur.dto.MyUserDto;
import org.azhur.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class MyUserConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    public ConsumerFactory<String, MyUserDto> myUserConsumerFactory() {
        Map<String , Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(MyUserDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MyUserDto> myUserKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MyUserDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(myUserConsumerFactory());
        return factory;
    }
}