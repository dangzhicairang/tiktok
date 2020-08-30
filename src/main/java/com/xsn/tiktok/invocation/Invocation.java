package com.xsn.tiktok.invocation;

import com.xsn.tiktok.invocation.specific.Worker;
import com.xsn.tiktok.invocation.whole.AbstractTiktokPlayer;
import com.xsn.tiktok.strategy.Strategy;

/**
 * Limitation 的顶级抽象
 * 表示一种执行 比如
 * 整个过程的执行 {@link AbstractTiktokPlayer}
 * 某一阶段过程的执行 {@link Worker}
 * 策略的执行 {@link Strategy}
 * 等等
 */
public interface Invocation {

    /**
     * 对应具体的执行逻辑处理
     */
    void invoke();
}
