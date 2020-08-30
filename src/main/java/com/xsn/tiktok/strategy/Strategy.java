package com.xsn.tiktok.strategy;

import com.xsn.tiktok.invocation.Invocation;
import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.limitation.LimitationRegister;
import com.xsn.tiktok.manager.ManagerRegister;

/**
 * 策略
 * 对于各种 {@link Limitation} 采取的对应策略
 */
public interface Strategy extends Invocation, LimitationRegister, ManagerRegister {

    boolean match();

    boolean canHandle();

    boolean handle();
}
