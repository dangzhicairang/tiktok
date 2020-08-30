package com.xsn.tiktok.invocation;

import com.xsn.tiktok.limitation.Limitation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
@Slf4j
class DefaultWorker implements Worker {

    private AbstractTiktokInvocation abstractTiktokInvocation;

    private List<Limitation> limitations;

    private boolean flag = true;

    public DefaultWorker(AbstractTiktokInvocation abstractTiktokInvocation) {
        assert abstractTiktokInvocation != null
                && abstractTiktokInvocation.getLimitations() != null;

        this.abstractTiktokInvocation = abstractTiktokInvocation;
        this.limitations = abstractTiktokInvocation.getLimitations();

        if (limitations.size() > 0) {
            limitations.stream().forEach(e -> e.setWorker(this));
        }
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
