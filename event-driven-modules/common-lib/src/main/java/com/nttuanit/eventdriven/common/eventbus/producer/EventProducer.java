package com.nttuanit.eventdriven.common.eventbus.producer;

import com.nttuanit.eventdriven.common.eventbus.exception.EventPublishException;
import com.nttuanit.eventdriven.common.eventbus.model.Event;

/**
 * Generic event
 *
 * @param <T>
 */
public interface EventProducer<T> {
  /**
   * Publish an event to event bus, throw {@link EventPublishException} if the operation fails
   *
   * @param event          - Event to publish
   * @param messageGroupId - Id of the message group to publish event to,
   *                       all events published into a group are guaranteed in order if SQS is FIFO
   * @throws EventPublishException
   */
  void publish(Event<T> event, String messageGroupId) throws EventPublishException;
}
