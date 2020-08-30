package com.xsn.tiktok.invocation;

import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultTiktokInvocation extends AbstractTiktokInvocation implements Time {

    private static final Object MUTEX = new Object();

    private Thread intervalThread;

    private long interval;

    public DefaultTiktokInvocation() {
        this(new ArrayList<Limitation>(), 900);
    }

    public DefaultTiktokInvocation(List<Limitation> limitations) {
        this(limitations, 900);
    }

    public DefaultTiktokInvocation(List<Limitation> limitations, long interval) {
        this.limitations = limitations;
        this.interval = interval;
    }

    public Thread getIntervalThread() {
        return intervalThread;
    }

    public void addTimeLimitation(Limitation limitation) {
        synchronized (MUTEX) {
            limitations.add(limitation);
        }
    }

    public void removeTimeLimitation(Limitation limitation) {
        if (limitations.contains(limitation)) {
            synchronized (MUTEX) {
                limitations.remove(limitation);
            }
        }
    }

    @Override
    public void prepare() {
        intervalThread = new Thread(() -> {

            for (Limitation limitation : limitations) {
                if (limitation instanceof Time) {
                    limitation.start();
                }
            }

            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(interval);
                } catch (InterruptedException e) {
                    break;
                }
                for (Limitation limitation : limitations) {
                    if (limitation instanceof Time) {
                        limitation.tryStop();
                    }
                }
            }
        });
    }

    @Override
    protected void play() throws InterruptedException {
        intervalThread.start();
        super.play();

    }

    @Override
    public void stop() {
        intervalThread.interrupt();
        log.info("stop play, go to sleep");
    }
}
