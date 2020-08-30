package com.xsn.tiktok.strategy;

import com.xsn.tiktok.invocation.Invocation;
import com.xsn.tiktok.limitation.Limitation;

/**
 * 心魔
 */
public interface Strategy {

    /**
     * 是否允许我们“拖延”
     * 如果不想拖延
     * 请 return false {@link NoWayStrategy}
     * @return
     */
    boolean canHandle();

    /**
     * 妥协
     * @param limitation
     */
    void handle(Limitation limitation);
}
