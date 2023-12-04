package cs2030.simulator;

import java.util.Optional;
import cs2030.util.Pair;

interface Event {
    Pair<Optional<Event>, Shop> execute(Shop shop);

    double getEventTime();

    int getPriority();
    /* Priority:
    EventStub: 0
    Leave: 1
    Done: 2
    Serve: 3
    Arrive: 4
    Wait: 5*/
    
    int getServerId();
}