package com.xsn.tiktok.invocation.specific;

import com.xsn.tiktok.invocation.whole.AbstractTiktokInvocation;
import com.xsn.tiktok.limitation.Limitation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
@Slf4j
public class DefaultWorker implements Worker {

    private AbstractTiktokInvocation abstractTiktokInvocation;

    private List<Limitation> limitations;

    private boolean flag;

    public DefaultWorker(AbstractTiktokInvocation abstractTiktokInvocation) {
        assert abstractTiktokInvocation != null
                && abstractTiktokInvocation.getLimitations() != null;

        this.abstractTiktokInvocation = abstractTiktokInvocation;
        this.limitations = abstractTiktokInvocation.getLimitations();
    }

    @Override
    public void invoke() throws InterruptedException {
        while (flag) {
            play();
            TimeUnit.SECONDS.sleep(5);
        }

        abstractTiktokInvocation.stop();
    }

    protected void play() {
        log.info("tiktok ~ tiktok ~");
    }

    @Override
    public void stop() {
        flag = false;
    }
}
