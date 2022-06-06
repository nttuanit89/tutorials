package com.nttuanit.eventdriven.common.eventbus.listerner;

import com.nttuanit.eventdriven.common.eventbus.consumer.EventConsumer;

public interface EventHandler {
  <T> void registerConsumer(EventConsumer<T> consumer);
}
