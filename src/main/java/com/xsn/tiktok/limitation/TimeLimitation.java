package com.xsn.tiktok.limitation;

import com.xsn.tiktok.invocation.Worker;
import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * 只许刷 overSeconds 秒
 * 多了不行！
 * 除非 {@link Strategy} 同意
 */
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

    public TimeLimitation() {
        this(800, 3);
    }

    public TimeLimitation(int overSeconds, int allOverTimes) {

        assert strategy == null;

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

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
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

    /**
     * 激烈的思想斗争
     * 如果想做个自制力强大的人
     * 请 setStrategy(null) 或 setStrategy(new NoWayStrategy(this))
     */
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
            log.info("over time, but handle");
            strategy.handle(this);
            return;
        }
    }
}
