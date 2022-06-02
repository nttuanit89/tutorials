package com.nttuanit.common.eventbus;

import com.nttuanit.common.eventbus.consumer.EventConsumer;
import com.nttuanit.common.eventbus.producer.EventProducer;

import javax.naming.OperationNotSupportedException;

public interface EventBus {
  /**
   * Create an event producer base on the Event Bus underlying infrastructure
   *
   * @param topicName - name of the topic to publish to
   * @param <T> - type of event data
   * @return an {@link EventProducer} with specified type
   */
  <T> EventProducer<T> createEventProducer(String topicName);

  /**
   * Register a consumer to Event Bus, the topic to subscribe to should be retrieved from the
   * consumer
   *
   * @param consumer - a {@link EventConsumer} to register
   * @param <T> - type of event data
   */
  <T> void registerConsumer(EventConsumer<T> consumer) throws OperationNotSupportedException;
}
