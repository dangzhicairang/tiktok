package com.xsn.tiktok.manager;

import com.xsn.tiktok.invocation.specific.WorkerRegister;
import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.limitation.LimitationRegister;
import com.xsn.tiktok.strategy.StrategyRegister;

/**
 * 对整个过程中各种限制条件 {@link Limitation} 的管理
 */
public interface Manager extends LimitationRegister, StrategyRegister, WorkerRegister {

    boolean match();
}
