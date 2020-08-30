package com.xsn.tiktok.invocation;

import com.xsn.tiktok.invocation.specific.Worker;
import com.xsn.tiktok.invocation.whole.AbstractTiktokInvocation;
import com.xsn.tiktok.strategy.Strategy;

public interface Invocation {

    void invoke() throws InterruptedException;
}
