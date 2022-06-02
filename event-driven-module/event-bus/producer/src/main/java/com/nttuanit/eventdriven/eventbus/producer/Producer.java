package com.nttuanit.eventdriven.eventbus.producer;

import com.nttuanit.eventdriven.common.eventbus.model.Event;
import com.nttuanit.eventdriven.common.eventbus.producer.EventProducer;
import com.nttuanit.eventdriven.eventbus.producer.message.EventNotifyInfo;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class Producer {
  private final EventProducer<EventNotifyInfo> producer;

  public void publish(EventNotifyInfo info) {
    String eventId = "1d9a4ed0-89d2-43b0-92b4-fc06b9b497ef";
    String groupId = "03b23bf9-3ee1-4ec7-9876-1e78e737";
    Event<EventNotifyInfo> snsEvent =
        Event.<EventNotifyInfo>builder()
            .eventId(eventId)
            .eventTime(Instant.now())
            .data(info)
            .build();
    producer.publish(snsEvent, groupId);
  }
}
