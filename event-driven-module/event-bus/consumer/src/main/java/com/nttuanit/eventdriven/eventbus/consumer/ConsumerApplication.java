package com.nttuanit.eventdriven.eventbus.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.nttuanit.eventdriven.eventbus.consumer")
@ConfigurationPropertiesScan(basePackages = "com.nttuanit.eventdriven.eventbus.consumer")
public class ConsumerApplication {
  public static void main(String... args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}
