package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class WaitSelfCheck implements Event {
    private final Customer customer;
    private final double eventTime;
    private final int serverId;
    private static final int PRIORITY = 5;

    WaitSelfCheck(Customer customer, double eventTime, int serverId) {
        this.customer = customer;
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

    Customer getCustomer() {
        return this.customer;
    }

    @Override 
    public int getServerId() {
        return this.serverId;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        // servers here have a current customer, but queue not full currently
        SelfCheck selfCheck = shop.getSelfCheckList().get(serverId - 1 - shop.getNumOfServers());
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f %d waits at self-check %d", eventTime,
            customer.getId(), serverId);
    }
}
