package cs2030.simulator;

class Statistics {
    private final double waitingTime;
    private final int cServed;
    private final int cLeft;

    Statistics() {
        this.waitingTime = 0;
        this.cServed = 0;
        this.cLeft = 0;
    }

    Statistics(double waitingTime, int cServed, int cLeft) {
        this.waitingTime = waitingTime;
        this.cServed = cServed;
        this.cLeft = cLeft;
    }

    Statistics addWaitingTime(double time) {
        return new Statistics(this.waitingTime + time, this.cServed, this.cLeft);
    }

    Statistics addServed() {
        return new Statistics(this.waitingTime, this.cServed + 1, this.cLeft);
    }

    Statistics addLeft() {
        return new Statistics(this.waitingTime, this.cServed, this.cLeft + 1);
    }

    @Override
    public String toString() {
        if (this.cServed == 0) {
            return "[0 0 0]";
        }
        return String.format("[%.3f %d %d]", this.waitingTime / this.cServed, 
            this.cServed, this.cLeft);
    }
}
