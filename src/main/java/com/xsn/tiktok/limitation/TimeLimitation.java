package com.xsn.tiktok.limitation;

import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
public class TimeLimitation implements Limitation, Time {

    private volatile Strategy strategy;

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

        if (strategy == null) {
            return true;
        }

        if (strategy.match() && strategy.canHandle()) {
            strategy.handle();
            return false;
        }

        return true;
    }
}
