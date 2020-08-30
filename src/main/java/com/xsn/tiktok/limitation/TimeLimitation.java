package com.xsn.tiktok.limitation;

import com.xsn.tiktok.invocation.specific.Worker;
import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

@Slf4j
public class TimeLimitation implements Limitation, Time {

    private Strategy strategy;

    private Worker worker;

    private Date start;

    private Date end;

    private int overSeconds;

    private int allOverTimes;

    private int currentOverTimes = 0;

    private boolean isStop = true;

    public TimeLimitation(Strategy strategy, Worker worker) {
        this(strategy, worker, 1800, 3);
    }

    public TimeLimitation(Strategy strategy, Worker worker,
                          int overSeconds, int allOverTimes) {

        assert strategy == null || worker == null;

        this.strategy = strategy;
        this.worker = worker;
        this.overSeconds = overSeconds;
        this.allOverTimes = allOverTimes;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getCurrentOverTimes() {
        return currentOverTimes;
    }

    public void setCurrentOverTimes(int currentOverTimes) {
        this.currentOverTimes = currentOverTimes;
    }

    public int getAllOverTimes() {
        return allOverTimes;
    }

    public void setAllOverTimes(int allOverTimes) {
        this.allOverTimes = allOverTimes;
    }

    @Override
    public void start() {
        if (isStop) {
            start = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(Calendar.SECOND, overSeconds);
            end = calendar.getTime();

            isStop = false;
        } else {
            log.warn("already started");
        }
    }

    @Override
    public boolean isOver() {
        return new Date().after(end);
    }

    @Override
    public boolean isStop() {
        return isStop;
    }

    @Override
    public void tryStop() {

        if (isStop) {
            log.warn("already stopped");
            return;
        }

        if (!isOver()) {
            log.warn("need not stopped");
            return;
        }

        if (strategy == null || !strategy.canHandle()) {
            worker.stop();
            return;
        }

        if (strategy.canHandle()) {
            strategy.handle(this);
            return;
        }
    }
}
