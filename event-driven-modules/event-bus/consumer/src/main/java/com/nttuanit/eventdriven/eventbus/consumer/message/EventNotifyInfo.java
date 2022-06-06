package com.nttuanit.eventdriven.eventbus.consumer.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventNotifyInfo {
  private UUID id;
  private String name;
  private Long age;
  private ZonedDateTime registeredTime;
  private BigDecimal amount;
  private Instant recordedTime;
}
