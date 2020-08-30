package com.xsn.tiktok.strategy;

import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.limitation.TimeLimitation;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * 允许延时 timeLimitation.getAllOverTimes 次
 * 每次延时 interval 秒
 */
@Slf4j
public class TimeStrategy implements Strategy, Time {

    private TimeLimitation timeLimitation;

    private int interval;

    public TimeStrategy(TimeLimitation timeLimitation) {
        this(timeLimitation, 1800);
    }

    public TimeStrategy(TimeLimitation timeLimitation, int interval) {
        assert timeLimitation != null && interval > 0;

        this.timeLimitation = timeLimitation;
        this.interval = interval;
    }

    @Override
    public boolean canHandle() {

        return timeLimitation != null
                && !timeLimitation.isStop()
                && timeLimitation.isOver()
                && timeLimitation.getAllOverTimes() > timeLimitation.getCurrentOverTimes();
    }

    @Override
    public void handle(Limitation limitation) {
        Date end = timeLimitation.getEnd();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.SECOND, interval);
        end = calendar.getTime();

        timeLimitation.setEnd(end);
        timeLimitation.setCurrentOverTimes(timeLimitation.getCurrentOverTimes() + 1);
    }

}
