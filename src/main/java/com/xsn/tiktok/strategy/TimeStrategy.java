package com.xsn.tiktok.strategy;

import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.manager.Manager;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeStrategy implements Strategy, Time {

    private Limitation limitation;

    private ThreadLocal<Integer> maxLimits;

    private ThreadLocal<Integer> currentTimes;

    private static final Object MUTEX = new Object();

    @Override
    public boolean match() {
        return limitation instanceof Time;
    }

    @Override
    public boolean canHandle() {
        return maxLimits.get() > 0 && currentTimes.get() < maxLimits.get();
    }

    @Override
    public boolean handle() {
        return false;
    }

    @Override
    public void invoke() {

    }

    @Override
    public void addLimitation(Limitation limitation) {
        if (this.limitation == null) {
            synchronized (MUTEX) {
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
            synchronized (MUTEX) {
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
    public void addManager(Manager manager) {

    }

    @Override
    public void removeManager(Manager manager) {

    }

    @Override
    public void changeManager(Manager manager) {

    }
}
