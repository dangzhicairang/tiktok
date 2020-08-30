package com.xsn.tiktok.strategy;

import com.xsn.tiktok.invocation.Invocation;
import com.xsn.tiktok.limitation.Limitation;

public interface Strategy {

    boolean canHandle();

    void handle(Limitation limitation);
}
