package com.nttuanit.eventbus.common.factory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.nttuanit.eventbus.common.consumer.EventConsumer;
import com.nttuanit.eventbus.common.producer.EventProducer;
import com.nttuanit.eventbus.common.producer.sns.SnsEventProducer;
import org.apache.commons.lang3.StringUtils;

import javax.naming.OperationNotSupportedException;

public class SnsEventBus extends BaseEventBus {
  private final AmazonSNSAsync snsClient;

  SnsEventBus(
      String snsEndpoint, String defaultRegion, String awsAccessKeyId, String awsAccessKeySecret) {
    this.snsClient =
        createAsyncClient(snsEndpoint, defaultRegion, awsAccessKeyId, awsAccessKeySecret);
  }

  @Override
  public <T> EventProducer<T> createEventProducer(String topicName) {
    return new SnsEventProducer<>(topicName, this.snsClient, this.messageConverter);
  }

  @Override
  public <T> void registerConsumer(EventConsumer<T> consumer)
      throws OperationNotSupportedException {
    throw new OperationNotSupportedException(
        "SNS consumer is not supported due to it non delivery guarantee nature.");
  }

  private AmazonSNSAsync createAsyncClient(
      String snsEndpoint, String region, String awsAccessKeyId, String awsAccessKeySecret) {
    AmazonSNSAsyncClientBuilder clientBuilder = AmazonSNSAsyncClientBuilder.standard();

    if (StringUtils.isNotBlank(snsEndpoint) && StringUtils.isNotBlank(region)) {
      AwsClientBuilder.EndpointConfiguration endpointConfiguration =
          new AwsClientBuilder.EndpointConfiguration(snsEndpoint, region);
      clientBuilder.withEndpointConfiguration(endpointConfiguration);
    }

    if (StringUtils.isNotBlank(awsAccessKeyId) && StringUtils.isNotBlank(awsAccessKeySecret)) {
      AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsAccessKeySecret);
      clientBuilder.withCredentials(new AWSStaticCredentialsProvider(credentials));
    }

    return clientBuilder.build();
  }
}
