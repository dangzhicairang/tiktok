package com.xsn.tiktok.strategy;

import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeStrategy implements Strategy, Time {

    private volatile Limitation limitation;

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
}
