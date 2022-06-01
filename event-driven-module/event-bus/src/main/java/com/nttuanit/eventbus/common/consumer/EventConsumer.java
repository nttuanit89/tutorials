package com.nttuanit.eventbus.common.consumer;

import com.nttuanit.eventbus.common.exception.EventConsumeException;
import com.nttuanit.eventbus.common.model.Event;

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
