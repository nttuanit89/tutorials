package com.nttuanit.eventbus.common.listerner.sqs;

import com.nttuanit.eventbus.common.consumer.EventConsumer;
import com.nttuanit.eventbus.common.listerner.EventHandler;
import com.nttuanit.eventbus.common.model.Event;
import io.awspring.cloud.messaging.listener.QueueMessageHandler;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.opentelemetry.extension.annotations.WithSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Event handler for SQS */
public class SqsEventHandler extends QueueMessageHandler implements EventHandler {
  private static final Logger LOG = LoggerFactory.getLogger(SqsEventHandler.class);
  private static final String SNS_PROPERTY_NAME_MESSAGE = "Message";
  private static final String SNS_PROPERTY_NAME_TYPE = "Type";
  private static final String SNS_PROPERTY_VALUE_TYPE = "Notification";

  private final MessageConverter messageConverter;

  public SqsEventHandler(
      MessageConverter messageConverter, SqsMessageDeletionPolicy sqsMessageDeletionPolicy) {
    super(List.of(messageConverter), sqsMessageDeletionPolicy);
    this.messageConverter = messageConverter;
  }

  @Override
  public <T> void registerConsumer(EventConsumer<T> consumer) {
    String topicName = consumer.getSubscribedTopic();
    try {
      Method method = consumer.getClass().getMethod("consume", Event.class);
      registerHandlerMethod(
          consumer,
          method,
          new MappingInformation(Set.of(topicName), SqsMessageDeletionPolicy.ON_SUCCESS));
    } catch (NoSuchMethodException exception) {
      LOG.error(String.format("Could not register consumer for topic '%s'", topicName), exception);
    }
  }

  @Override
  @WithSpan
  public void handleMessage(Message<?> message) throws MessagingException {
    Map<String, String> propertiesMap =
        (Map<String, String>) this.messageConverter.fromMessage(message, Map.class);

    if (propertiesMap != null
        && propertiesMap.containsKey(SNS_PROPERTY_NAME_TYPE)
        && SNS_PROPERTY_VALUE_TYPE.equals(propertiesMap.get(SNS_PROPERTY_NAME_TYPE))
        && propertiesMap.containsKey(SNS_PROPERTY_NAME_MESSAGE)) {
      MessageHeaderAccessor headerAccessor = MessageHeaderAccessor.getMutableAccessor(message);
      message =
          MessageBuilder.createMessage(
              propertiesMap.get(SNS_PROPERTY_NAME_MESSAGE), headerAccessor.getMessageHeaders());
    }

    super.handleMessage(message);
  }
}
