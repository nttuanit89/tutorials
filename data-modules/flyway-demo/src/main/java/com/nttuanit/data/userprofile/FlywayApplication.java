package com.nttuanit.data.userprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages = "com.nttuanit.data.userprofile")
@ConfigurationPropertiesScan(basePackages = "com.nttuanit.data.userprofile")
//@EnableRetry
//@EntityScan(basePackages = {"com.nttuanit.common.crud", "com.nttuanit.**.entity"})
public class FlywayApplication {
  public static void main(String... args) {
    SpringApplication.run(FlywayApplication.class, args);
  }
}
