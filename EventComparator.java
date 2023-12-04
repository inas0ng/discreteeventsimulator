package cs2030.simulator;

import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    public int compare(Event i, Event j) {
        if (i.getEventTime() > j.getEventTime()) {
            return 1;
        }
        if (i.getEventTime() < j.getEventTime()) {
            return -1;
        }
        return i.getPriority() - j.getPriority();
    }
}
