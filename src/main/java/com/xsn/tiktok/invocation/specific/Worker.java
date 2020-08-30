package com.xsn.tiktok.invocation.specific;

import com.xsn.tiktok.invocation.Invocation;
import com.xsn.tiktok.limitation.LimitationRegister;

public interface Worker extends Invocation, LimitationRegister {

    boolean canStop();
}
