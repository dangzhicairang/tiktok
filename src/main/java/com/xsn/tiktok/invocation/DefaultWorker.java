package com.xsn.tiktok.invocation;

import com.xsn.tiktok.limitation.Limitation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 默认的 {@link Worker} 实现类
 * 一直刷抖音，直到 {@link Limitation} 告诉我不要再刷了
 */
@Setter
@Getter
@Slf4j
class DefaultWorker implements Worker {

    // 持有 AbstractTiktokInvocation 的引用是为了回调 stop 方法
    private AbstractTiktokInvocation abstractTiktokInvocation;

    // 刷抖音途中的各种条件限制
    private List<Limitation> limitations;

    // 一直刷
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

    /**
     * 一直刷，直到 flag 标识被 （{@link Invocation}) 修改
     * 回调 abstractTiktokInvocation.stop()
     * @throws InterruptedException
     */
    @Override
    public void invoke() throws InterruptedException {
        while (flag) {
            play();
            TimeUnit.SECONDS.sleep(5);
        }

        abstractTiktokInvocation.stop();
    }

    // 刷抖音
    protected void play() {
        log.info("tiktok ~ tiktok ~");
    }

    // 修改表示，该方法由 {@link Invocation} 调用
    @Override
    public void stop() {
        flag = false;
    }
}
