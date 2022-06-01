package com.nttuanit.eventbus.common.listerner.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.nttuanit.eventbus.common.listerner.EventHandler;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import io.awspring.cloud.messaging.listener.QueueMessageHandler;
import io.awspring.cloud.messaging.listener.SimpleMessageListenerContainer;

public class SqsListenerContainerFactory extends SimpleMessageListenerContainerFactory {
  public SqsListenerContainerFactory(AmazonSQSAsync sqsClient, EventHandler eventHandler) {
    if (eventHandler instanceof QueueMessageHandler) {
      QueueMessageHandler messageHandler = (QueueMessageHandler) eventHandler;
      this.setAmazonSqs(sqsClient);
      this.setQueueMessageHandler(messageHandler);
    }
  }

  public SimpleMessageListenerContainer createEventListenerContainer() {
    SimpleMessageListenerContainer container = createSimpleMessageListenerContainer();
    container.setMessageHandler(getQueueMessageHandler());
    return container;
  }
}
