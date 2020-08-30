package com.xsn.tiktok.invocation;

import com.xsn.tiktok.invocation.Invocation;

public interface Worker extends Invocation {

    void stop();
}
