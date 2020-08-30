package com.xsn.tiktok.limitation;

import com.xsn.tiktok.invocation.specific.Worker;
import com.xsn.tiktok.manager.ManagerRegister;
import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.manager.Manager;
import com.xsn.tiktok.strategy.StrategyRegister;

/**
 * 各种限制因素
 * 比如
 * 时间的限制
 * 环境的限制
 * 自身条件的限制
 * 等等
 */
public interface Limitation extends StrategyRegister, ManagerRegister {

    /**
     * 是否超出了限制
     * @return
     */
    boolean isOver();

    boolean canStop();

}
