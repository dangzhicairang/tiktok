package com.xsn.tiktok.invocation.specific;

import com.xsn.tiktok.invocation.whole.AbstractTiktokPlayer;
import com.xsn.tiktok.limitation.Limitation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Setter
@Getter
@Slf4j
public class DefaultWorker implements Worker {

    private AbstractTiktokPlayer abstractTiktokPlayer;

    private List<Limitation> limitations;

    private ThreadLocal<Boolean> flagHolder;

    private static final Object LIMITATION_MUTEX = new Object();


    @Override
    public void invoke() {
        while (flagHolder.get()) {
            play();
        }
    }

    protected void play() {
        log.info("tiktok ~ tiktok ~");
    }

    @Override
    public boolean canStop() {
        return false;
    }
}
