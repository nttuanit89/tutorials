package com.nttuanit.eventbus.common.listerner;

import com.nttuanit.eventbus.common.consumer.EventConsumer;

public interface EventHandler {
  <T> void registerConsumer(EventConsumer<T> consumer);
}
