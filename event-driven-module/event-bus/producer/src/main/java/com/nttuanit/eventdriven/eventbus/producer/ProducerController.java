package com.nttuanit.eventdriven.eventbus.producer;

import com.nttuanit.eventdriven.common.eventbus.model.Event;
import com.nttuanit.eventdriven.eventbus.producer.message.EventNotifyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produce")
public class ProducerController {
  private final ProducerHelper producerHelper;

  @GetMapping("/{message}")
  public String sendMessage(@PathVariable String message) {
    producerHelper.publish(createEvent(), "test1");
    return "Message sent successfully";
  }

  private Event<EventNotifyInfo> createEvent() {
    Random random = new Random();
    Event.EventBuilder<EventNotifyInfo> eventBuilder = Event.builder();

    return eventBuilder
        .eventId(UUID.randomUUID().toString())
        .eventTime(Instant.now())
        .retries(0)
        .data(
            new EventNotifyInfo(
                UUID.randomUUID(),
                String.format("Sample string %s", random.nextInt()),
                random.nextLong(),
                ZonedDateTime.now(),
                BigDecimal.valueOf(random.nextDouble()),
                Instant.now()))
        .build();
  }
}
