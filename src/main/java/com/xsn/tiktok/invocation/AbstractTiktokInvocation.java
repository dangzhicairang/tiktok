package com.xsn.tiktok.invocation;

import com.xsn.tiktok.invocation.Invocation;
import com.xsn.tiktok.limitation.Limitation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 刷抖音 的抽象类
 * 同理，可以在 @{link Invocation} 下抽象各种 “万恶之源”
 * 运用了模板方法（ModelMethod）设计模式，由子类实现具体细节
 */
@Getter
@Setter
public abstract class AbstractTiktokInvocation implements Invocation {

    // 阻止我们刷抖音的条条框框
    protected List<Limitation> limitations;

    /**
     * 模板方法
     * prepare 准备工作
     * play 开刷
     * stop 别刷辣
     */
    @Override
    public void invoke() {

        prepare();

        try {
            play();
        } catch (InterruptedException e) {
            e.printStackTrace();
            stop();
        }

    }

    public abstract void prepare();

    /**
     * 委托给 DefaultWorker
     * protected，子类可以复写
     * @throws InterruptedException
     */
    protected void play() throws InterruptedException {
        try {
            new DefaultWorker(this).invoke();
        } catch (InterruptedException e) {
            e.printStackTrace();
            stop();
        }
    }

    public abstract void stop();
}
