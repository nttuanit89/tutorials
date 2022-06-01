package com.nttuanit.eventbus.common.exception;

public class EventPublishException extends RuntimeException {
  public EventPublishException(String message) {
    super(message);
  }

  public EventPublishException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
