package com.nttuanit.common.eventbus.listerner;

import com.nttuanit.common.eventbus.consumer.EventConsumer;
import com.nttuanit.common.eventbus.consumer.EventConsumer;

public interface EventHandler {
  <T> void registerConsumer(EventConsumer<T> consumer);
}
