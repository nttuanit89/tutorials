package com.nttuanit.common.eventbus.consumer;

import com.nttuanit.common.eventbus.exception.EventConsumeException;
import com.nttuanit.common.eventbus.model.Event;

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
