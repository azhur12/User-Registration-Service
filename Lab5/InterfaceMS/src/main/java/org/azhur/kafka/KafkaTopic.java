package org.azhur.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("new-owners").build();
    }

    @Bean NewTopic topic2() {
        return TopicBuilder.name("owners_id").build();
    }

    @Bean NewTopic topic3() {
        return TopicBuilder.name("new-cats").build();
    }

    @Bean NewTopic topic4() {
        return TopicBuilder.name("cats_id").build();
    }

    @Bean NewTopic topic5() {
        return TopicBuilder.name("new-users").build();
    }
}
