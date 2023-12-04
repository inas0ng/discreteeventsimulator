package cs2030.util;

import java.util.PriorityQueue;
import java.util.Comparator;

public class PQ<T> {
    private final PriorityQueue<T> pq;

    public PQ(Comparator<? super T> cmp) {
        this.pq = new PriorityQueue<T>(cmp);
    }

    public PQ(PriorityQueue<T> pq) {
        this.pq = new PriorityQueue<T>(pq);
    }

    public PQ<T> add(T elem) {
        PQ<T> newPQ = new PQ<T>(this.pq);
        newPQ.pq.add(elem);
        return newPQ;
    }

    public Pair<T, PQ<T>> poll() {
        PQ<T> newPQ = new PQ<T>(this.pq);
        T elem = newPQ.pq.poll();
        return Pair.<T, PQ<T>>of(elem, newPQ);
    }

    public boolean isEmpty() {
        return this.pq.isEmpty();
    }

    public int size() {
        return this.pq.size();
    }

    @Override
    public String toString() {
        return this.pq.toString();
    }
}
