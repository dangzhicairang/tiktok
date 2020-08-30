package com.xsn.tiktok.invocation.whole;

import com.xsn.tiktok.invocation.Invocation;
import com.xsn.tiktok.invocation.specific.DefaultWorker;
import com.xsn.tiktok.limitation.Limitation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class AbstractTiktokInvocation implements Invocation {

    protected List<Limitation> limitations;

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
