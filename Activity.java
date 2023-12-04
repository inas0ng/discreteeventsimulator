package cs2030.simulator;

import cs2030.util.ImList;

class Activity {
    private final Customer currentCus;
    private final double nextAvailTime;
    private final ImList<Customer> queueCus;
    private final int queueMax;

    Activity() {
        // When a server is free, and no qmax given
        this.currentCus = new Customer(-1, -1.0);
        this.nextAvailTime = -1.0;
        this.queueCus = ImList.<Customer>of();
        this.queueMax = 1;
    }

    Activity(int queueMax) {
        // When a server is free, and qmax given
        this.currentCus = new Customer(-1, -1.0);
        this.nextAvailTime = -1.0;
        this.queueCus = ImList.<Customer>of();
        this.queueMax = queueMax;
    }

    Activity(Customer currentCus, double nextAvailTime) {
        // When a server is attending to a customer, no queue, no qmax
        this.currentCus = currentCus;
        this.nextAvailTime = nextAvailTime;
        this.queueCus = ImList.<Customer>of();
        this.queueMax = 1;
    }

    Activity(Customer currentCus, double nextAvailTime, int queueMax) {
        // When a server is attending to a customer, no queue, yes qmax
        this.currentCus = currentCus;
        this.nextAvailTime = nextAvailTime;
        this.queueCus = ImList.<Customer>of();
        this.queueMax = queueMax;
    }

    Activity(Customer currentCus, double nextAvailTime, ImList<Customer> queue) {
        // When a server is attending to a customer, has queue, no qmax
        this.currentCus = currentCus;
        this.nextAvailTime = nextAvailTime;
        this.queueCus = queue;
        this.queueMax = 1;
    }

    Activity(Customer currentCus, double nextAvailTime, ImList<Customer> queue, int queueMax) {
        // When a server is attending to a customer, has queue, yes qmax
        this.currentCus = currentCus;
        this.nextAvailTime = nextAvailTime;
        this.queueCus = queue;
        this.queueMax = queueMax;
    }


    boolean isFree() {
        if (nextAvailTime == -1.0) {
            return true;
        } 
        return false;
    }

    boolean hasQueue() {
        if (queueCus.size() == 0) {
            return false;
        }
        return true;
    }

    boolean queueFull() {
        if (queueCus.size() == queueMax) {
            return true;
        } else {
            return false;
        }
    }

    Customer getCurrentCustomer() {
        return currentCus;
    }

    double getNextAvailTime() {
        return nextAvailTime;
    }

    ImList<Customer> getQueueCus() {
        return queueCus;
    }

    int getQueueMax() {
        return queueMax;
    }

    Activity addToQ(Customer newCus) {
        return new Activity(currentCus, nextAvailTime, queueCus.add(newCus), queueMax);
    }

    Activity serveCus(Customer cus, double untilTime) {
        return new Activity(cus, untilTime, queueCus, queueMax);
    }

    Activity poll() {
        return new Activity(currentCus, nextAvailTime, queueCus.remove(0).second(), queueMax);
    }

    Activity setNextAvailTime(double newTime) {
        return new Activity(currentCus, newTime, queueCus, queueMax);
    }
}
