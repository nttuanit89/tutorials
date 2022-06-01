package com.nttuanit.eventbus.common.producer.sns;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.nttuanit.eventbus.common.exception.EventPublishException;
import com.nttuanit.eventbus.common.model.Event;
import com.nttuanit.eventbus.common.producer.EventProducer;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.messaging.converter.MessageConverter;

import java.util.HashMap;
import java.util.Map;

import static io.awspring.cloud.messaging.core.TopicMessageChannel.MESSAGE_DEDUPLICATION_ID_HEADER;
import static io.awspring.cloud.messaging.core.TopicMessageChannel.MESSAGE_GROUP_ID_HEADER;

public class SnsEventProducer<T> implements EventProducer<T> {
  private final NotificationMessagingTemplate notificationMessagingTemplate;
  private final String topicName;

  public SnsEventProducer(
      String topicName, AmazonSNSAsync snsAsync, MessageConverter messageConverter) {
    this.notificationMessagingTemplate = createMessagingTemplate(snsAsync, messageConverter);
    this.topicName = topicName;
  }

  @Override
  @WithSpan
  public void publish(Event<T> event, String messageGroupId) throws EventPublishException {
    try {
      Map<String, Object> headers = new HashMap<>();
      headers.put(MESSAGE_GROUP_ID_HEADER, messageGroupId);
      headers.put(MESSAGE_DEDUPLICATION_ID_HEADER, event.getEventId().toString());
      notificationMessagingTemplate.convertAndSend(topicName, event, headers);
    } catch (Exception exception) {
      throw new EventPublishException(
          String.format("Failed to send event %s to event bus", event.getEventId()), exception);
    }
  }

  private NotificationMessagingTemplate createMessagingTemplate(
      AmazonSNSAsync snsAsync, MessageConverter messageConverter) {
    NotificationMessagingTemplate messagingTemplate = new NotificationMessagingTemplate(snsAsync);
    messagingTemplate.setMessageConverter(messageConverter);

    return messagingTemplate;
  }
}
