package com.nttuanit.eventbus.common.factory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nttuanit.eventbus.common.EventBus;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

abstract class BaseEventBus implements EventBus {
  protected final MessageConverter messageConverter;

  BaseEventBus() {
    this.messageConverter = createSqsMessageConverter();
  }

  private MessageConverter createSqsMessageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
    messageConverter.setObjectMapper(objectMapper);
    messageConverter.setStrictContentTypeMatch(false);
    messageConverter.setSerializedPayloadClass(String.class);

    return messageConverter;
  }
}
