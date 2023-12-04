package cs2030.simulator;

import cs2030.util.Pair;
import cs2030.util.Lazy;
import java.util.function.Supplier;

class Customer {
    private final int id;
    private final double arrivalTime;
    private final Lazy<Double> serviceTime;

    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = Lazy.<Double>of(() -> 1.0);
    }

    Customer(int id, Pair<Double, Supplier<Double>> inputTimes) {
        this.id = id;
        this.arrivalTime = inputTimes.first();
        this.serviceTime = Lazy.<Double>of(inputTimes.second());
    }

    int getId() {
        return this.id;
    }

    double getArrivalTime() {
        return this.arrivalTime;
    }

    double getServiceTime() {
        return this.serviceTime.get();
    }

    @Override 
    public String toString() {
        return "" + this.id;
    }
}
