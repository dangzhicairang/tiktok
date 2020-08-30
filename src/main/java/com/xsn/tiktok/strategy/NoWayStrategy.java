package com.xsn.tiktok.strategy;

import com.xsn.tiktok.limitation.Limitation;

public class NoWayStrategy implements Strategy {

    @Override
    public boolean canHandle() {
        return false;
    }

    @Override
    public void handle(Limitation limitation) {
        throw new UnsupportedOperationException();
    }
}
