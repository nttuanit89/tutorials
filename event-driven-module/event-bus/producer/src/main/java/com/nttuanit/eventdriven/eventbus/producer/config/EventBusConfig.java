package com.nttuanit.eventdriven.eventbus.producer.config;

import com.nttuanit.eventdriven.common.eventbus.EventBus;
import com.nttuanit.eventdriven.common.eventbus.factory.EventBusFactory;
import com.nttuanit.eventdriven.common.eventbus.producer.EventProducer;
import com.nttuanit.eventdriven.eventbus.producer.message.EventNotifyInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

@Configuration
@ConditionalOnProperty(value = "event-bus.enable", havingValue = "true")
public class EventBusConfig {

  @Bean(name = "snsEventBus")
  EventBus createSnsEventBus(GenericApplicationContext applicationContext) {
    return EventBusFactory.getSnsEventBusFactory(applicationContext);
  }

  @ConditionalOnBean(EventBus.class)
  @Bean
  EventProducer<EventNotifyInfo> eventProducer(
      @Qualifier("snsEventBus") EventBus eventBus,
      @Value("${event-bus.sns.topics.exampleTopic}") String topic) {
    return eventBus.createEventProducer(topic);
  }
}
