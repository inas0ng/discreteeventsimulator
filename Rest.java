package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Rest implements Event {
    private final double eventTime;
    private final double restDuration;
    private final int serverId;
    private static final int PRIORITY = -2;

    Rest(double eventTime, double restDuration, int serverId) {
        this.eventTime = eventTime;
        this.restDuration = restDuration;
        this.serverId = serverId;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public double getEventTime() {
        return this.eventTime;
    }

    @Override 
    public int getServerId() {
        return this.serverId;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        Server server = shop.getServerList().get(serverId - 1);
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(
            new RestDone(eventTime + restDuration, serverId)), 
            shop.changeServerStatus(server.serveCus(
            new Customer(-1, -1.0), eventTime + restDuration)));
    }

    @Override
    public String toString() {
        return String.format("%.3f %d rests for %.3f", eventTime, serverId, restDuration);
    }
}
