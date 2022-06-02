package com.nttuanit.eventdriven.common.eventbus.consumer;

import com.nttuanit.eventdriven.common.eventbus.exception.EventConsumeException;
import com.nttuanit.eventdriven.common.eventbus.model.Event;

public interface EventConsumer<T> {
  /**
   * Consume an event, throw an {@link EventConsumeException} if operation fails
   *
   * @param event Event to process
   * @throws EventConsumeException
   */
  void consume(Event<T> event) throws EventConsumeException;

  /**
   * Subscribe to a topic
   *
   * @param topicName Topic name
   */
  void subscribe(String topicName);

  /**
   * Get subscribed topic
   *
   * @return
   */
  String getSubscribedTopic();
}
