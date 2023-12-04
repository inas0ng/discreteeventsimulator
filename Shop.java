package cs2030.simulator;

import java.util.List;
import cs2030.util.ImList;

class Shop {
    private final ImList<Server> serverList;
    private final int numOfServers;
    private final ImList<SelfCheck> selfCheckList;
    private final int numOfSelfChecks;
    private final ImList<Customer> selfCheckQueue;
    private final int qMax;

    Shop(List<Server> serverList) {
        this.serverList = ImList.<Server>of(serverList);
        this.numOfServers = serverList.size();
        this.selfCheckList = ImList.<SelfCheck>of();
        this.numOfSelfChecks = 0;
        this.selfCheckQueue = ImList.<Customer>of();
        this.qMax = -1; // won't be used
    }

    Shop(ImList<Server> serverList) {
        this.serverList = ImList.<Server>of(serverList);
        this.numOfServers = serverList.size();
        this.selfCheckList = ImList.<SelfCheck>of();
        this.numOfSelfChecks = 0;
        this.selfCheckQueue = ImList.<Customer>of();
        this.qMax = -1; // won't be used
    }

    Shop(ImList<Server> serverList, ImList<SelfCheck> selfCheckList) {
        this.serverList = ImList.<Server>of(serverList);
        this.numOfServers = serverList.size();
        this.selfCheckList = ImList.<SelfCheck>of(selfCheckList);
        this.numOfSelfChecks = selfCheckList.size();
        this.selfCheckQueue = ImList.<Customer>of();
        this.qMax = -1; // won't be used
    }

    Shop(ImList<Server> serverList, ImList<SelfCheck> selfCheckList,
        ImList<Customer> selfCheckQueue) {
        this.serverList = ImList.<Server>of(serverList);
        this.numOfServers = serverList.size();
        this.selfCheckList = ImList.<SelfCheck>of(selfCheckList);
        this.numOfSelfChecks = selfCheckList.size();
        this.selfCheckQueue = ImList.<Customer>of(selfCheckQueue);
        this.qMax = -1; // won't be used
    }

    Shop(List<Server> serverList, int qMax) {
        this.serverList = ImList.<Server>of(serverList);
        this.numOfServers = serverList.size();
        this.selfCheckList = ImList.<SelfCheck>of();
        this.numOfSelfChecks = 0;
        this.selfCheckQueue = ImList.<Customer>of();
        this.qMax = qMax;
    }

    Shop(ImList<Server> serverList, int qMax) {
        this.serverList = ImList.<Server>of(serverList);
        this.numOfServers = serverList.size();
        this.selfCheckList = ImList.<SelfCheck>of();
        this.numOfSelfChecks = 0;
        this.selfCheckQueue = ImList.<Customer>of();
        this.qMax = qMax;
    }

    Shop(ImList<Server> serverList, ImList<SelfCheck> selfCheckList, int qMax) {
        this.serverList = ImList.<Server>of(serverList);
        this.numOfServers = serverList.size();
        this.selfCheckList = ImList.<SelfCheck>of(selfCheckList);
        this.numOfSelfChecks = selfCheckList.size();
        this.selfCheckQueue = ImList.<Customer>of();
        this.qMax = qMax;
    }

    Shop(ImList<Server> serverList, ImList<SelfCheck> selfCheckList,
        ImList<Customer> selfCheckQueue, int qMax) {
        this.serverList = ImList.<Server>of(serverList);
        this.numOfServers = serverList.size();
        this.selfCheckList = ImList.<SelfCheck>of(selfCheckList);
        this.numOfSelfChecks = selfCheckList.size();
        this.selfCheckQueue = ImList.<Customer>of(selfCheckQueue);
        this.qMax = qMax;
    }

    int getNumOfServers() {
        return this.numOfServers;
    }

    ImList<Server> getServerList() {
        return this.serverList;
    }

    int getNumOfSelfChecks() {
        return this.numOfSelfChecks;
    }

    int getQMax() {
        return this.qMax;
    }

    ImList<SelfCheck> getSelfCheckList() {
        return this.selfCheckList;
    }

    ImList<Customer> getSelfCheckQueue() {
        return this.selfCheckQueue;
    }

    Shop changeServerStatus(Server server) {
        return new Shop(this.serverList.set(server.getId() - 1, server), 
            this.selfCheckList, this.selfCheckQueue, this.qMax);
    }

    Shop changeSelfCheckStatus(SelfCheck selfCheck) {
        return new Shop(this.serverList, this.selfCheckList
            .set(selfCheck.getId() - 1 - numOfServers, selfCheck), this.selfCheckQueue, this.qMax);
    } 

    Shop addQueue(Customer cus) {
        return new Shop(this.serverList, this.selfCheckList,
            this.selfCheckQueue.add(cus), this.qMax);
    }

    Shop pollQueue() {
        return new Shop(this.serverList, this.selfCheckList,
            this.selfCheckQueue.remove(0).second(), this.qMax);
    }

    @Override
    public String toString() {
        return "" + this.serverList;
    }
}
