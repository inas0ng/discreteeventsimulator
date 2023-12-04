package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class EventStub implements Event {
    private final Customer customer;
    private final double eventTime;

    EventStub(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = eventTime;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public double getEventTime() {
        return this.eventTime;
    }

    @Override 
    public int getServerId() {
        return -1;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", eventTime);
    }
}
