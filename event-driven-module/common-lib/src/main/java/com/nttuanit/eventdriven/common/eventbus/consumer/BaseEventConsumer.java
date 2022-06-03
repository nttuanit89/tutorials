package com.nttuanit.eventdriven.common.eventbus.consumer;

import com.nttuanit.eventdriven.common.eventbus.EventBus;

import javax.naming.OperationNotSupportedException;

public abstract class BaseEventConsumer<T> implements EventConsumer<T> {
  private String subscribedTopic;

  public BaseEventConsumer(String subscribedTopic, EventBus eventBus)
      throws OperationNotSupportedException {
    this.subscribedTopic = subscribedTopic;
    eventBus.registerConsumer(this);
  }

  @Override
  public void subscribe(String topicName) {
    this.subscribedTopic = topicName;
  }

  @Override
  public String getSubscribedTopic() {
    return this.subscribedTopic;
  }
}
