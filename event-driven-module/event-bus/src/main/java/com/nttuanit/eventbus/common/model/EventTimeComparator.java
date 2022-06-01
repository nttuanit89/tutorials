package com.nttuanit.eventbus.common.model;

import java.util.Comparator;

public class EventTimeComparator implements Comparator<Event> {
  @Override
  public int compare(Event event1, Event event2) {
    int result = checkNull(event1, event2);

    result = result != 0 ? result : checkNull(event1.getEventTime(), event2.getEventTime());

    return result != 0 ? result : event1.getEventTime().compareTo(event2.getEventTime());
  }

  private int checkNull(Object obj1, Object obj2) {
    return obj1 == null ? 1 : obj2 == null ? -1 : 0;
  }
}
