package com.xsn.tiktok.invocation.specific;

import com.xsn.tiktok.invocation.Invocation;

public interface Worker extends Invocation {

    boolean canStop();
}
