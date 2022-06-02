package com.nttuanit.eventdriven.eventbus.producer.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@Value
public class EventNotifyInfo {
  String content;
  String subject;
}
