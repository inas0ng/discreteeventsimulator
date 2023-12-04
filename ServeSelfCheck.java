package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class ServeSelfCheck implements Event {
    private final Customer customer;
    private final double eventTime;
    private final int serverId;
    private static final int PRIORITY = 3;

    ServeSelfCheck(Customer customer, double eventTime, int serverId) {
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
        SelfCheck selfCheck = shop.getSelfCheckList().get(serverId - 1 - shop.getNumOfServers());
        double cusServiceTime = this.customer.getServiceTime();
        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(
            new DoneSelfCheck(this.getCustomer(), this.getEventTime() + cusServiceTime, serverId)), 
            shop.changeSelfCheckStatus(selfCheck.serveCus(customer, eventTime + cusServiceTime)));
    }

    @Override
    public String toString() {
        return String.format("%.3f %d serves by self-check %d", eventTime,
            customer.getId(), serverId);
    }
}
