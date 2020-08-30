package com.xsn.tiktok.limitation;

import com.xsn.tiktok.invocation.Worker;
import com.xsn.tiktok.strategy.Strategy;

/**
 * 刷抖音途中的各种限制条件，比如 时间，身体状况，环境等等
 */
public interface Limitation {

    // 条件生效
    void start();

    // 是否超过限制了（太晚了，眼睛疼等等）
    boolean isOver();

    // 是否停止了
    boolean isStop();

    /**
     * 试图停止（因为可能被 {@link Strategy} 处理）
     * 准时睡觉？不存在
     */
    void tryStop();

    /**
     * 保存 {@link Worker} 的引用，让它停止刷抖音
     * @param worker
     */
    void setWorker(Worker worker);

    /**
     * 指定 {@link Strategy}
     * @param strategy
     */
    void setStrategy(Strategy strategy);

}
