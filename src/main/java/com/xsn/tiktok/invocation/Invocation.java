package com.xsn.tiktok.invocation;

/**
 * 执行接口，比如 整个刷抖音的过程
 * 提供了 invoke 方法
 * 但具体的细节，比如 与时间博弈 的过程委托给 {@link Worker}
 */
public interface Invocation {

    void invoke() throws InterruptedException;
}
