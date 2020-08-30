package com.xsn.tiktok.invocation.whole;

import com.xsn.tiktok.invocation.Invocation;
import com.xsn.tiktok.limitation.Limitation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 整个 tiktok 执行的抽象类
 * 使用模板方法定义整个流程，由子类具体实现
 */
@Getter
@Setter
public abstract class AbstractTiktokPlayer implements Invocation {

    private List<Limitation> limitations;

    @Override
    public void invoke() {

        canStart();

        prepare();

        play();

    }

    abstract boolean canStart();

    abstract void prepare();

    final void play() {

    }

    abstract void stop();
}
