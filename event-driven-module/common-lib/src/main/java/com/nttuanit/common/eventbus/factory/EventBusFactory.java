package com.nttuanit.common.eventbus.factory;

import com.nttuanit.common.eventbus.EventBus;
import org.springframework.context.support.GenericApplicationContext;

public class EventBusFactory {
  private static SqsEventBus sqsEventBus;
  private static SnsEventBus snsEventBus;

  public static synchronized EventBus getSqsEventBusFactory(
      GenericApplicationContext applicationContext,
      String sqsEndpoint,
      String defaultRegion,
      String awsAccessKeyId,
      String awsAccessKeySecret) {
    if (sqsEventBus == null) {
      sqsEventBus =
          new SqsEventBus(
              applicationContext, sqsEndpoint, defaultRegion, awsAccessKeyId, awsAccessKeySecret);
    }
    return sqsEventBus;
  }

  public static synchronized EventBus getSqsEventBusFactory(
      GenericApplicationContext applicationContext) {
    if (sqsEventBus == null) {
      String sqsEndpoint =
          applicationContext.getEnvironment().getProperty("event-bus.sqs.endpoint", "");
      String region = applicationContext.getEnvironment().getProperty("event-bus.sqs.region", "");
      String accessKeyId = applicationContext.getEnvironment().getProperty("aws.key.id", "");
      String accessKeySecret =
          applicationContext.getEnvironment().getProperty("aws.key.secret", "");
      sqsEventBus =
          new SqsEventBus(applicationContext, sqsEndpoint, region, accessKeyId, accessKeySecret);
    }
    return sqsEventBus;
  }

  public static synchronized EventBus getSnsEventBusFactory(
      String sqsEndpoint, String defaultRegion, String awsAccessKeyId, String awsAccessKeySecret) {
    if (snsEventBus == null) {
      snsEventBus = new SnsEventBus(sqsEndpoint, defaultRegion, awsAccessKeyId, awsAccessKeySecret);
    }
    return snsEventBus;
  }

  public static synchronized EventBus getSnsEventBusFactory(
      GenericApplicationContext applicationContext) {
    if (snsEventBus == null) {
      String sqsEndpoint =
          applicationContext.getEnvironment().getProperty("event-bus.sns.endpoint", "");
      String region = applicationContext.getEnvironment().getProperty("event-bus.sns.region", "");
      String accessKeyId = applicationContext.getEnvironment().getProperty("aws.key.id", "");
      String accessKeySecret =
          applicationContext.getEnvironment().getProperty("aws.key.secret", "");
      snsEventBus = new SnsEventBus(sqsEndpoint, region, accessKeyId, accessKeySecret);
    }
    return snsEventBus;
  }
}
