package com.xsn.tiktok.limitation;

import com.xsn.tiktok.invocation.Worker;
import com.xsn.tiktok.strategy.Strategy;

public interface Limitation {

    void start();

    boolean isOver();

    boolean isStop();

    void tryStop();

    void setWorker(Worker worker);

    void setStrategy(Strategy strategy);

}
