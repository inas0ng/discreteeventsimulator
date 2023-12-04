package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.PQ;
import cs2030.util.Pair;
import java.util.List;

public class Simulate2 {
    private final PQ<Event> pq;
    private final Shop shop;

    public Simulate2(int noOfServers, List<Double> arrivalTime) {
        PQ<Event> pqEvent = new PQ<Event>(new EventComparator());
        int noOfCustomers = 0;
        for (Double aTime : arrivalTime) {
            noOfCustomers += 1;
            Event newEvent = new EventStub(new Customer(noOfCustomers, aTime), aTime);
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
        while (!pair.second().isEmpty()) {
            pair = pair.second().poll();
            str += pair.first() + "\n";
        }
        str += "-- End of Simulation --";
        return str;
    }

    @Override
    public String toString() {
        return "Queue: " + this.pq + "; Shop: " + this.shop;
    }
}
