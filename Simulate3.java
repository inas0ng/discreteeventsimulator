package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.PQ;
import cs2030.util.Pair;
import java.util.List;

public class Simulate3 {
    private final PQ<Event> pq;
    private final Shop shop;

    public Simulate3(int noOfServers, List<Double> arrivalTime) {
        PQ<Event> pqEvent = new PQ<Event>(new EventComparator());
        int noOfCustomers = 0;
        for (Double aTime : arrivalTime) {
            noOfCustomers += 1;
            Event newEvent = new Arrive(new Customer(noOfCustomers, aTime), aTime);
            pqEvent = pqEvent.add(newEvent);
        }
        this.pq = pqEvent;
        ImList<Server> imServers = ImList.<Server>of();
        for (int i = 1; i <= noOfServers; i++) {
            imServers = imServers.add(new Server(i));
        }
        this.shop = new Shop(imServers);
    }

    public String run() {
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
            str += pair.first() + "\n";
            currentPQ = pair.second();
            event = pair.first().execute(newShop).first().orElse(fakeEvent);
            newShop = pair.first().execute(newShop).second();

            if (event.getEventTime() != -1.0) {
                pair = Pair.<Event, PQ<Event>>of(pair.first(), pair.second().add(event));
            }

            if (pair.first().getPriority() == 2) {
                // Done event
                int serverId = pair.first().getServerId();
                Server server = newShop.getServerList().get(serverId - 1);
                if (!server.hasQueue()) {
                    newShop = newShop.changeServerStatus(server.toFree(server.getQueueMax()));
                } else {
                    pair = Pair.<Event, PQ<Event>>of(pair.first(), pair.second()
                            .add(new Serve(server.getQueueCus().remove(0).first(),
                            pair.first().getEventTime(), serverId)));
                    newShop = newShop.changeServerStatus(server.poll());
                }
            }
        }
        str += "-- End of Simulation --";
        return str;
    }

    @Override
    public String toString() {
        return "Queue: " + this.pq + "; Shop: " + this.shop;
    }
}
