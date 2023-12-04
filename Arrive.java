package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

class Arrive implements Event {
    private final Customer customer;
    private final double eventTime;
    private static final int PRIORITY = 4;

    Arrive(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = eventTime;
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
        return -1;
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        for (Server server : shop.getServerList()) {
            if (server.isFree()) {
                return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(
                    new Serve(this.getCustomer(), this.getEventTime(), server.getId())), shop);
            }
        }

        for (SelfCheck selfCheck : shop.getSelfCheckList()) {
            if (selfCheck.isFree()) {
                return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(
                    new ServeSelfCheck(this.getCustomer(), this.getEventTime(), 
                                            selfCheck.getId())), shop);
            }
        }

        for (Server server : shop.getServerList()) {
            if (!server.queueFull()) {
                return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(
                    new Wait(this.getCustomer(), this.getEventTime(), server.getId())), shop);
            }
        }

        if (shop.getNumOfSelfChecks() > 0 && (shop.getSelfCheckQueue().size() < shop.getQMax())) {
            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(
                new WaitSelfCheck(this.getCustomer(), this.getEventTime(),
                    shop.getNumOfServers() + 1)), shop.addQueue(this.getCustomer()));
        }

        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(
            new Leave(this.getCustomer(), this.getEventTime())), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f %d arrives", eventTime, customer.getId());
    }
}
