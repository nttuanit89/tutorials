package com.nttuanit.eventdriven.eventbus.producer;

import com.nttuanit.eventdriven.common.eventbus.model.Event;
import com.nttuanit.eventdriven.common.eventbus.producer.EventProducer;
import com.nttuanit.eventdriven.eventbus.producer.message.EventNotifyInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;

@ConditionalOnProperty(value = "event-bus.enable", havingValue = "true")
@Component
@RequiredArgsConstructor
@Slf4j
public class ProducerHelper {
  private final EventProducer<EventNotifyInfo> producer;

  public void publish(Event<EventNotifyInfo> event, String messageGroupId) {
    producer.publish(event, messageGroupId);
  }
}
