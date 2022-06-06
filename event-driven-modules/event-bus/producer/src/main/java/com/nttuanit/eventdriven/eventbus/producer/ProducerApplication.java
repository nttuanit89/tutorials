package com.nttuanit.eventdriven.eventbus.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.nttuanit.eventdriven.eventbus.producer")
@ConfigurationPropertiesScan(basePackages = "com.nttuanit.eventdriven.eventbus.producer")
public class ProducerApplication {

  public static void main(String... args) {
    SpringApplication.run(ProducerApplication.class, args);
  }
}
