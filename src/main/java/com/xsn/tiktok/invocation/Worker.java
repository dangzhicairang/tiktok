package com.xsn.tiktok.invocation;

import com.xsn.tiktok.invocation.Invocation;

/**
 * 在 {@link AbstractTiktokInvocation} 中，
 * 将 刷抖音 过程中的 博弈 委托给该类处理
 * Worker 同时也继承了 {@link Invocation} 接口
 */
public interface Worker extends Invocation {

    /**
     * 求求我不要再刷抖音了
     */
    void stop();
}
