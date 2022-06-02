package com.nttuanit.common.eventbus.factory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.nttuanit.common.eventbus.consumer.EventConsumer;
import com.nttuanit.common.eventbus.listerner.sqs.SqsEventHandler;
import com.nttuanit.common.eventbus.listerner.sqs.SqsListenerContainerFactory;
import com.nttuanit.common.eventbus.producer.EventProducer;
import com.nttuanit.common.eventbus.producer.sqs.SqsEventProducer;
import com.nttuanit.common.eventbus.listerner.EventHandler;
import io.awspring.cloud.messaging.listener.SimpleMessageListenerContainer;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.GenericApplicationContext;

public class SqsEventBus extends BaseEventBus {
  private final GenericApplicationContext applicationContext;
  private final AmazonSQSAsync sqsClient;
  private Boolean listenerStarted;

  SqsEventBus(
      GenericApplicationContext applicationContext,
      String sqsEndpoint,
      String defaultRegion,
      String awsAccessKeyId,
      String awsAccessKeySecret) {
    this.applicationContext = applicationContext;
    this.sqsClient =
        createAsyncClient(sqsEndpoint, defaultRegion, awsAccessKeyId, awsAccessKeySecret);
    this.listenerStarted = Boolean.FALSE;
  }

  @Override
  public <T> EventProducer<T> createEventProducer(String topicName) {
    return new SqsEventProducer<>(topicName, this.sqsClient, this.messageConverter);
  }

  @Override
  public <T> void registerConsumer(EventConsumer<T> consumer) {
    startListen();

    if (consumer != null && consumer.getSubscribedTopic() != null) {
      EventHandler eventHandler =
          this.applicationContext.getBeanFactory().getBean(EventHandler.class);
      eventHandler.registerConsumer(consumer);
    }
  }

  private synchronized void startListen() {
    if (!this.listenerStarted) {
      this.applicationContext.registerBean(
          EventHandler.class,
          this::createEventHandler,
          beanDefinition -> {
            beanDefinition.setPrimary(true);
            beanDefinition.setAutowireCandidate(true);
          });

      EventHandler eventHandler =
          this.applicationContext.getBeanFactory().getBean(EventHandler.class);
      SqsListenerContainerFactory factory =
          new SqsListenerContainerFactory(this.sqsClient, eventHandler);

      this.applicationContext.registerBean(
          SimpleMessageListenerContainer.class,
          factory::createEventListenerContainer,
          beanDefinition -> {
            beanDefinition.setPrimary(true);
            beanDefinition.setAutowireCandidate(true);
          });

      this.listenerStarted = true;
    }
  }

  private AmazonSQSAsync createAsyncClient(
      String sqsEndpoint, String region, String awsAccessKeyId, String awsAccessKeySecret) {
    AmazonSQSAsyncClientBuilder clientBuilder = AmazonSQSAsyncClientBuilder.standard();

    if (StringUtils.isNotBlank(sqsEndpoint) && StringUtils.isNotBlank(region)) {
      AwsClientBuilder.EndpointConfiguration endpointConfiguration =
          new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, region);
      clientBuilder.withEndpointConfiguration(endpointConfiguration);
    }

    if (StringUtils.isNotBlank(awsAccessKeyId) && StringUtils.isNotBlank(awsAccessKeySecret)) {
      AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessKeySecret);
      clientBuilder.withCredentials(new AWSStaticCredentialsProvider(credentials));
    }

    return clientBuilder.build();
  }

  private EventHandler createEventHandler() {
    return new SqsEventHandler(this.messageConverter, SqsMessageDeletionPolicy.ON_SUCCESS);
  }
}
