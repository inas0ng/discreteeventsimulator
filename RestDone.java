package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class RestDone implements Event {
    private final double eventTime;
    private final int serverId;
    private static final int PRIORITY = -3;

    RestDone(double eventTime, int serverId) {
        this.eventTime = eventTime;
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
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>empty(), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f server %d done resting", eventTime, serverId);
    }
}
