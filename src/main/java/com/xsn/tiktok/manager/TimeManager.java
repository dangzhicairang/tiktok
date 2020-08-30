package com.xsn.tiktok.manager;

import com.xsn.tiktok.invocation.specific.Worker;
import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class TimeManager implements Manager, Time {

    private Limitation limitation;

    private Worker worker;

    private static final Object LIMITATION_MUTEX = new Object();

    private static final Object WORKER_MUTEX = new Object();

    private ThreadLocal<Date> startDate;



    @Override
    public boolean match() {
        return limitation instanceof Time;
    }

    @Override
    public void addLimitation(Limitation limitation) {
        if (this.limitation == null) {
            synchronized (LIMITATION_MUTEX) {
                if (this.limitation == null) {
                    this.limitation = limitation;
                } else {
                    log.warn("already has a limitation");
                }
            }
        } else {
            log.warn("already has a limitation");
        }
    }

    @Override
    public void removeLimitation(Limitation limitation) {
        if (this.limitation != null) {
            synchronized (LIMITATION_MUTEX) {
                if (this.limitation != null) {
                    this.limitation = null;
                }
            }
        }
    }

    @Override
    public void changeLimitation(Limitation limitation) {
        this.limitation = limitation;
    }

    @Override
    public void addStrategy(Strategy strategy) {
        if (limitation != null) {
            synchronized (LIMITATION_MUTEX) {
                limitation.addStrategy(strategy);
            }
        }
    }

    @Override
    public void removeStrategy(Strategy strategy) {
        if (limitation != null) {
            synchronized (LIMITATION_MUTEX) {
                limitation.removeStrategy(strategy);
            }
        }
    }

    @Override
    public void addWorker(Worker worker) {
        if (this.worker == null) {
            synchronized (WORKER_MUTEX) {
                if (this.worker == null) {
                    this.worker = worker;
                } else {
                    log.warn("already has a worker");
                }
            }
        } else {
            log.warn("already has a worker");
        }
    }

    @Override
    public void removeWorker(Worker worker) {
        if (this.worker != null) {
            synchronized (WORKER_MUTEX) {
                if (this.worker != null) {
                    this.worker = null;
                }
            }
        }
    }

    @Override
    public void changeWorker(Worker worker) {
        this.worker = worker;
    }
}
