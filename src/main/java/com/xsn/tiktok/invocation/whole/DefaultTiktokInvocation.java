package com.xsn.tiktok.invocation.whole;

import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.support.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DefaultTiktokInvocation extends AbstractTiktokInvocation implements Time {

    private static final Object MUTEX = new Object();

    private Runnable runnable;

    private final Thread intervalThread = new Thread(runnable);

    private long interval;

    public DefaultTiktokInvocation() {
        this(new ArrayList<Limitation>(), 900);
    }

    public DefaultTiktokInvocation(List<Limitation> limitations) {
        this(limitations, 900);
    }

    public DefaultTiktokInvocation(List<Limitation> limitations, long interval) {
        assert limitations != null && interval > 10;
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
        runnable = () -> {
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
        };
    }

    @Override
    protected void play() throws InterruptedException {
        intervalThread.start();
        intervalThread.join();
        super.play();

    }

    @Override
    public void stop() {
        intervalThread.interrupt();
    }
}
