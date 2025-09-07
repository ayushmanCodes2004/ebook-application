package com.ebook.payment_service.Config;


import com.ebook.payment_service.Events.OrderCreatedEvent;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
//
//    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
//    private String bootstrapServers;
//
//    @Bean
//    public ConsumerFactory<String, com.ebook.payment_service.Events.OrderCreatedEvent> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-service-group");
//
//        JsonDeserializer<com.ebook.payment_service.Events.OrderCreatedEvent> jsonDeserializer =
//                new JsonDeserializer<>();
//        jsonDeserializer.setRemoveTypeHeaders(false);
//        jsonDeserializer.addTrustedPackages("com.ebook.payment_service.Events","com.ebook.order_service.Events");
//
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
//
//
//
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, com.ebook.payment_service.Events.OrderCreatedEvent> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, com.ebook.payment_service.Events.OrderCreatedEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//}
