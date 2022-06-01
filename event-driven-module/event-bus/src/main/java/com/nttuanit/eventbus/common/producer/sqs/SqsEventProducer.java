package com.nttuanit.eventbus.common.producer.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.nttuanit.eventbus.common.exception.EventPublishException;
import com.nttuanit.eventbus.common.model.Event;
import com.nttuanit.eventbus.common.producer.EventProducer;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.messaging.converter.MessageConverter;

import java.util.HashMap;
import java.util.Map;

import static io.awspring.cloud.messaging.core.TopicMessageChannel.MESSAGE_DEDUPLICATION_ID_HEADER;
import static io.awspring.cloud.messaging.core.TopicMessageChannel.MESSAGE_GROUP_ID_HEADER;

public class SqsEventProducer<T> implements EventProducer<T> {
  private final QueueMessagingTemplate messagingTemplate;
  private final String topicName;

  public SqsEventProducer(
      String topicName, AmazonSQSAsync sqsAsync, MessageConverter messageConverter) {
    this.messagingTemplate = createMessagingTemplate(sqsAsync, messageConverter);
    this.topicName = topicName;
  }

  @Override
  @WithSpan
  public void publish(Event<T> event, String messageGroupId) throws EventPublishException {
    try {
      Map<String, Object> headers = new HashMap<>();
      headers.put(MESSAGE_GROUP_ID_HEADER, messageGroupId);
      headers.put(MESSAGE_DEDUPLICATION_ID_HEADER, event.getEventId().toString());
      messagingTemplate.convertAndSend(topicName, event, headers);
    } catch (Exception exception) {
      throw new EventPublishException(
          String.format("Failed to send event %s to event bus", event.getEventId()), exception);
    }
  }

  private QueueMessagingTemplate createMessagingTemplate(
      AmazonSQSAsync sqsAsync, MessageConverter messageConverter) {
    QueueMessagingTemplate messagingTemplate = new QueueMessagingTemplate(sqsAsync);
    messagingTemplate.setMessageConverter(messageConverter);

    return messagingTemplate;
  }
}
