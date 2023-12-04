package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Lazy;
import java.util.function.Supplier;

class SelfCheck extends Server {

    SelfCheck(int id) {
        super(id, 0); // 0 for queueMax = 0, there is no indiv queue
    }

    SelfCheck(int id, Activity status) {
        super(id, status, 0);
    }

    @Override
    SelfCheck serveCus(Customer cus, double untilTime) {
        return new SelfCheck(this.getId(), this.getStatus().serveCus(cus, untilTime));
    }

    @Override
    SelfCheck toFree() {
        return new SelfCheck(this.getId(), new Activity(0));
    }

    @Override
    boolean isHuman() {
        return false;
    }
}
