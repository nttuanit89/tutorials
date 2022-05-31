package com.nttuanit.jib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.nttuanit.jib")
@ConfigurationPropertiesScan(basePackages = "com.nttuanit.jib")
public class Application {
  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }
}
