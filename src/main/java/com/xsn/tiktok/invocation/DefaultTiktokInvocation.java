package com.xsn.tiktok.invocation;

import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.support.Time;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * {@link AbstractTiktokInvocation} 的默认实现
 * 启动一个线程（因为 娱乐demo，就没用线程池管理了）定时检查
 * {@link Limitation} 条件是否超限（比如时间到了，眼睛瞎了，手机没电了等）
 * 如果超限，但有对应的 {@link Strategy} 可以处理，则交由其处理（延时、换眼睛、充电等）
 * 如果处理生效，继续刷。否则睡觉8
 */
@Slf4j
public class DefaultTiktokInvocation extends AbstractTiktokInvocation implements Time {

    // 锁
    private static final Object MUTEX = new Object();

    // 轮询线程
    private Thread intervalThread;

    // 轮询间隔
    private long interval;

    private long startTime;

    private long endTime;

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

    // 添加条件（突然困的不行了）
    public void addTimeLimitation(Limitation limitation) {
        synchronized (MUTEX) {
            limitations.add(limitation);
        }
    }

    // 删除条件（突然通知明天不上班了）
    public void removeTimeLimitation(Limitation limitation) {
        if (limitations.contains(limitation)) {
            synchronized (MUTEX) {
                limitations.remove(limitation);
            }
        }
    }

    // 轮询线程逻辑
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

    /**
     * 启动轮询线程
     * 调用父类的 play（即委托给 {@link DefaultWorker})
     * @throws InterruptedException
     */
    @Override
    protected void play() throws InterruptedException {

        startTime = System.currentTimeMillis();

        intervalThread.start();
        super.play();
    }

    // 睡觉
    @Override
    public void stop() {
        intervalThread.interrupt();
        log.info("stop play, go to sleep");

        endTime = System.currentTimeMillis();
        long speed = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
        log.info("speed " + speed + " s");
    }
}
