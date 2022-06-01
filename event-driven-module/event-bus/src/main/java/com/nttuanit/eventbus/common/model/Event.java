package com.nttuanit.eventbus.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

/**
 * Generic event that is used in event bus, which provide common properties for an event
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Event<T> {
  private String eventId;
  private Instant eventTime;
  private T data;
  private int retries;
  private Long version;

  public void increaseRetryTime() {
    this.retries++;
  }
}
