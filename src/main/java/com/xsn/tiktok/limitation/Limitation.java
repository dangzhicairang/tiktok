package com.xsn.tiktok.limitation;

public interface Limitation {

    void start();

    boolean isOver();

    boolean isStop();

    void tryStop();

}
