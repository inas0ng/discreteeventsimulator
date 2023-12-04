package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Poll implements Event {
    private final double eventTime;
    private final int serverId;
    private static final int PRIORITY = -2;

    Poll(double eventTime, int serverId) {
        this.eventTime = eventTime;
        this.serverId = serverId;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public double getEventTime() {
        return eventTime;
    }

    Customer getCustomer() {
        return new Customer(-1, -1.0);
    }

    @Override 
    public int getServerId() {
        return this.serverId;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        Server server = shop.getServerList().get(serverId - 1);
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), 
                shop.changeServerStatus(server.poll()));
    }

    @Override
    public String toString() {
        return String.format("poll %d", serverId);
    }
}
