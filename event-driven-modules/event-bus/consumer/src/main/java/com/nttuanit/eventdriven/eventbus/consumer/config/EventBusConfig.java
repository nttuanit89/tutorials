package com.nttuanit.eventdriven.eventbus.consumer.config;

import com.nttuanit.eventdriven.common.eventbus.EventBus;
import com.nttuanit.eventdriven.common.eventbus.factory.EventBusFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

@Configuration
@ConditionalOnProperty(value = "event-bus.enable", havingValue = "true")
public class EventBusConfig {

  @Bean(name = "sqsEventBus")
  EventBus createSqsEventBus(GenericApplicationContext applicationContext) {
    return EventBusFactory.getSqsEventBusFactory(applicationContext);
  }
}
