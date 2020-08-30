package com.xsn.tiktok.strategy;

import com.xsn.tiktok.invocation.Invocation;
import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.limitation.LimitationRegister;

/**
 * 策略
 * 对于各种 {@link Limitation} 采取的对应策略
 */
public interface Strategy extends Invocation, LimitationRegister {

    boolean match();

    boolean canHandle();

    boolean handle();
}
