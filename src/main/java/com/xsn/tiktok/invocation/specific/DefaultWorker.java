package com.xsn.tiktok.invocation.specific;

import com.xsn.tiktok.invocation.whole.AbstractTiktokPlayer;
import com.xsn.tiktok.limitation.Limitation;
import com.xsn.tiktok.strategy.Strategy;
import com.xsn.tiktok.manager.Manager;
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

    public DefaultWorker(AbstractTiktokPlayer abstractTiktokPlayer) {
        this(abstractTiktokPlayer
                , abstractTiktokPlayer.getManagerList());
    }

    public DefaultWorker(AbstractTiktokPlayer abstractTiktokPlayer
                            , List<Manager> managerList) {

        assert abstractTiktokPlayer != null
                && abstractTiktokPlayer.getLimitations() != null;

        this.abstractTiktokPlayer = abstractTiktokPlayer;
        this.limitations = limitations;
    }

    @Override
    public boolean canStop() {
        boolean result = false;

        for (Limitation limitation : limitations) {
            if (limitation.isOver()) {
                /*Strategy strategy = limitation.getStrategy();
                Manager manager = limitation.getManager();

                if (strategy == null || !strategy.match(limitation)) {
                    log.warn("there were no any available strategy for limitation");
                    return true;
                }

                if (manager == null || !manager.match(limitation)) {
                    log.warn("there were no any available manager for limitation");
                    return true;
                }

                if (strategy.match(limitation) && manager.match(limitation)) {
                    if ()
                    result = strategy.handle(limitation);
                }*/

            } else {
                continue;
            }
        }

        return result;
    }

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
    public void addLimitation(Limitation limitation) {
        synchronized (LIMITATION_MUTEX) {
            limitations.add(limitation);
        }
    }

    @Override
    public void removeLimitation(Limitation limitation) {
        synchronized (LIMITATION_MUTEX) {
            limitations.remove(limitation);
        }
    }
}
