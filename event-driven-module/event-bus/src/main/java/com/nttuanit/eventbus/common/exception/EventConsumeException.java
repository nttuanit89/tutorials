package com.nttuanit.eventbus.common.exception;

public class EventConsumeException extends RuntimeException {
  public EventConsumeException(String message) {
    super(message);
  }

  public EventConsumeException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
