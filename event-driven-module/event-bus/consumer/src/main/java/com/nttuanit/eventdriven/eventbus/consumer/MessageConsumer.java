package com.nttuanit.eventdriven.eventbus.consumer;

import com.nttuanit.eventdriven.common.eventbus.EventBus;
import com.nttuanit.eventdriven.common.eventbus.consumer.BaseEventConsumer;
import com.nttuanit.eventdriven.common.eventbus.exception.EventConsumeException;
import com.nttuanit.eventdriven.common.eventbus.model.Event;
import com.nttuanit.eventdriven.eventbus.consumer.message.EventNotifyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;

@Slf4j
@Component
public class MessageConsumer extends BaseEventConsumer<EventNotifyInfo> {
  @Value("${event-bus.sqs.queues.message-changed}")
  private String subscribedTopic;

  @Autowired
  public MessageConsumer(
      @Value("${event-bus.sqs.queues.message-changed}") String subscribedTopic,
      @Qualifier("sqsEventBus") EventBus eventBus)
      throws OperationNotSupportedException {
    super(subscribedTopic, eventBus);
  }

  @Override
  public void consume(Event<EventNotifyInfo> event) throws EventConsumeException {
    log.info("Message Received using event bus listener: " + event.getData().getName());
  }
}
