package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.PQ;
import cs2030.util.Pair;
import java.util.List;
import java.util.function.Supplier;
import java.util.Optional;

public class Simulate8 {
    private final PQ<Event> pq;
    private final Shop shop;
    private static final int REST = -2;
    private static final int RESTDONE = -3;

    public Simulate8(int noOfServers, int noOfSelfChecks, List<Pair<Double,
        Supplier<Double>>> inputTimes, int qmax, Supplier<Double> restTimes) {
        PQ<Event> pqEvent = new PQ<Event>(new EventComparator());
        int noOfCustomers = 0;
        for (Pair<Double, Supplier<Double>> inputTime : inputTimes) {
            noOfCustomers += 1;
            Event newEvent = new Arrive(new Customer(noOfCustomers, inputTime), inputTime.first());
            pqEvent = pqEvent.add(newEvent);
        }
        this.pq = pqEvent;
        ImList<Server> imServers = ImList.<Server>of();
        for (int i = 1; i <= noOfServers; i++) {
            imServers = imServers.add(new Server(i, qmax, restTimes));
        }
        ImList<SelfCheck> imSelfChecks = ImList.<SelfCheck>of();
        for (int i = 1; i <= noOfSelfChecks; i++) {
            imSelfChecks = imSelfChecks.add(new SelfCheck(i + noOfServers));
        }
        this.shop = new Shop(imServers, imSelfChecks, qmax);
    }

    public String run() {
        Statistics stats = new Statistics();
        String str = "";
        Pair<Event, PQ<Event>> pair = pq.poll();
        str += pair.first() + "\n";
        PQ<Event> currentPQ = pair.second();
        Event fakeEvent = new EventStub(new Customer(-1, -1.0), -1.0);
        Event event = pair.first().execute(shop).first().orElse(fakeEvent);
        Shop newShop = pair.first().execute(shop).second();
        if (event.getEventTime() != -1.0) {
            pair = Pair.<Event, PQ<Event>>of(pair.first(), pair.second().add(event));
        }
        while (!pair.second().isEmpty()) {
            pair = pair.second().poll();
            if (pair.first().getPriority() > REST) {
                str += pair.first() + "\n";
            }
            currentPQ = pair.second();
            Pair<Optional<Event>, Shop> executed = pair.first().execute(newShop);
            event = executed.first().orElse(fakeEvent);
            newShop = executed.second();
            if (event.getPriority() == 1) {
                stats = stats.addLeft();
            }
            if (event.getPriority() == 2) {
                stats = stats.addServed();
            }
            if (event.getEventTime() != -1.0) {
                pair = Pair.<Event, PQ<Event>>of(pair.first(), pair.second().add(event));
            }

            int serverId = pair.first().getServerId();
            Server server = new SelfCheck(-1);
            if (serverId <= shop.getNumOfServers() && serverId > 0) {
                server = newShop.getServerList().get(serverId - 1);
            }

            if (pair.first().getPriority() == 2 && server.isHuman()) {
                // Done event

                double serverRest = server.getRestTime(); // Roll to see if server rests
                if (serverRest == 0.0) { // No rest
                    if (!server.hasQueue()) {
                        newShop = newShop.changeServerStatus(server.toFree(
                            server.getQueueMax(), server.getLazyRest()));
                    } else {
                        pair = Pair.<Event, PQ<Event>>of(pair.first(), pair.second()
                                .add(new Serve(server.getQueueCus().remove(0).first(),
                                pair.first().getEventTime(), serverId)));
                        stats = stats.addWaitingTime(
                            pair.first().getEventTime() - server.getQueueCus()
                            .remove(0).first().getArrivalTime());
                        newShop = newShop.changeServerStatus(server.poll());
                    }
                } else {
                    // If rest & no queue, next available time += rest time
                    // If rest & queue, next customer serve event time += rest time
                    pair = Pair.<Event, PQ<Event>>of(pair.first(), pair.second()
                            .add(new Rest(pair.first().getEventTime(), serverRest, serverId)));
                }
            }

            if (pair.first().getPriority() == RESTDONE && server.isHuman()) {
                // Done with resting
                if (!server.hasQueue()) {
                    newShop = newShop.changeServerStatus(server.toFree(
                        server.getQueueMax(), server.getLazyRest()));
                } else {
                    pair = Pair.<Event, PQ<Event>>of(pair.first(), pair.second()
                            .add(new Serve(server.getQueueCus().remove(0).first(),
                            pair.first().getEventTime(), serverId))
                            .add(new Poll(pair.first().getEventTime(), serverId)));
                    stats = stats.addWaitingTime(
                        pair.first().getEventTime() - server.getQueueCus()
                        .remove(0).first().getArrivalTime());
                }
            }

            if (pair.first().getPriority() == 2 && !server.isHuman()) {
                // DoneSelfCheck event
                SelfCheck selfCheck = newShop.getSelfCheckList().get(
                    serverId - 1 - shop.getNumOfServers());
                if (newShop.getSelfCheckQueue().size() == 0) {
                    newShop = newShop.changeSelfCheckStatus(selfCheck.toFree());
                } else {
                    pair = Pair.<Event, PQ<Event>>of(pair.first(), pair.second()
                            .add(new ServeSelfCheck(newShop.getSelfCheckQueue()
                            .remove(0).first(), pair.first().getEventTime(), serverId)));
                    stats = stats.addWaitingTime(
                        pair.first().getEventTime() - newShop.getSelfCheckQueue()
                        .remove(0).first().getArrivalTime());
                    newShop = newShop.pollQueue();
                }
            }

        }
        str += stats;
        return str;
    }

    @Override
    public String toString() {
        return "Queue: " + this.pq + "; Shop: " + this.shop;
    }
}
