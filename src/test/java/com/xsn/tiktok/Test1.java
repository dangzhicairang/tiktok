package com.xsn.tiktok;

import com.xsn.tiktok.invocation.DefaultTiktokInvocation;
import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.limitation.TimeLimitation;
import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.strategy.TimeStrategy;
import org.junit.Test;

import java.util.ArrayList;

public class Test1 {

    @Test
    public void test1() {

        TimeLimitation timeLimitation = new TimeLimitation(30, 2);
        Strategy strategy = new TimeStrategy(timeLimitation, 20);
        timeLimitation.setStrategy(strategy);

        new DefaultTiktokInvocation(new ArrayList<Limitation>() {
            {
                add(timeLimitation);
            }
        }, 10).invoke();
    }
}
