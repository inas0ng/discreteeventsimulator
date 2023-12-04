package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Lazy;
import java.util.function.Supplier;

class Server {
    private final int id;
    private final Activity status;
    private final int queueMax;
    private final Supplier<Double> restTime;

    Server(int id) {
        this.id = id;
        this.status = new Activity();
        this.queueMax = 1;
        this.restTime = () -> 0.0;
    }

    Server(int id, Activity status) {
        this.id = id;
        this.status = status;
        this.queueMax = 1;
        this.restTime = () -> 0.0;
    }

    Server(int id, int queueMax) {
        this.id = id;
        this.status = new Activity(queueMax);
        this.queueMax = queueMax;
        this.restTime = () -> 0.0;
    }

    Server(int id, Activity status, int queueMax) {
        this.id = id;
        this.status = status;
        this.queueMax = queueMax;
        this.restTime = () -> 0.0;
    }

    Server(int id, int queueMax, Supplier<Double> restTime) {
        this.id = id;
        this.status = new Activity(queueMax);
        this.queueMax = queueMax;
        this.restTime = restTime;
    }

    Server(int id, Activity status, int queueMax, Supplier<Double> restTime) {
        this.id = id;
        this.status = status;
        this.queueMax = queueMax;
        this.restTime = restTime;
    }

    boolean isHuman() {
        return true;
    }

    int getId() {
        return this.id;
    }

    Activity getStatus() {
        return this.status;
    }

    Customer getCurrentCustomer() {
        return this.status.getCurrentCustomer();
    }

    double getNextAvailTime() {
        return this.status.getNextAvailTime();
    }

    ImList<Customer> getQueueCus() {
        return this.status.getQueueCus();
    }

    int getQueueMax() {
        return this.status.getQueueMax();
    }

    double getRestTime() {
        return this.restTime.get();
    }

    Supplier<Double> getLazyRest() {
        return this.restTime;
    }

    boolean isFree() {
        return this.status.isFree();
    }

    boolean hasQueue() {
        return this.status.hasQueue();
    }

    boolean queueFull() {
        return this.status.queueFull();
    }

    Server addToQ(Customer newCus) {
        return new Server(id, this.status.addToQ(newCus), queueMax, restTime);
    }

    Server serveCus(Customer cus, double untilTime) {
        return new Server(id, this.status.serveCus(cus, untilTime), queueMax, restTime);
    }

    Server toFree() {
        return new Server(this.id); // this function should not be called
    }

    Server toFree(int queueMax) {
        return new Server(this.id, new Activity(queueMax));
    }

    Server toFree(int queueMax, Supplier<Double> supp) {
        return new Server(this.id, new Activity(queueMax), queueMax, supp);
    }

    Server poll() {
        return new Server(this.id, this.status.poll(), queueMax, restTime);
    }

    Server setNextAvailTime(double newTime) {
        return new Server(this.id, this.status.setNextAvailTime(newTime), queueMax, restTime);
    }

    @Override
    public String toString() {
        return "" + this.id;
    }
}
