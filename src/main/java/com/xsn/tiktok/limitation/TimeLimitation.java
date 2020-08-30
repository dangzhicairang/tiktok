package com.xsn.tiktok.limitation;

import com.xsn.tiktok.manager.Manager;
import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
public class TimeLimitation implements Limitation, Time {

    private List<Strategy> strategyList;

    private volatile Manager manager;

    private static final Object MANAGER_MUTEX = new Object();

    private static final Object STRATEGY_MUTEX = new Object();

    private ThreadLocal<Date> dateHolder;

    public TimeLimitation(Date date) {
        dateHolder.set(date);
    }

    @Override
    public boolean isOver() {
        return new Date().after(dateHolder.get());
    }

    @Override
    public boolean canStop() {
        return false;
    }

    @Override
    public void addManager(Manager manager) {
        if (this.manager == null) {
            synchronized (MANAGER_MUTEX) {
                if (this.manager == null) {
                    this.manager = manager;
                } else {
                    log.warn("already has a manager");
                }
            }
        } else {
            log.warn("already has a manager");
        }
    }

    @Override
    public void removeManager(Manager manager) {
        if (this.manager != null) {
            synchronized (MANAGER_MUTEX) {
                if (this.manager != null) {
                    this.manager = null;
                }
            }
        }
    }

    @Override
    public void changeManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void addStrategy(Strategy strategy) {
        synchronized (STRATEGY_MUTEX) {
            strategyList.add(strategy);
        }
    }

    @Override
    public void removeStrategy(Strategy strategy) {
        synchronized (STRATEGY_MUTEX) {
            strategyList.remove(strategy);
        }
    }
}
