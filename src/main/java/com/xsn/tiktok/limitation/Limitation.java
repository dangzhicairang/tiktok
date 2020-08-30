package com.xsn.tiktok.limitation;

/**
 * 各种限制因素
 * 比如
 * 时间的限制
 * 环境的限制
 * 自身条件的限制
 * 等等
 */
public interface Limitation {

    /**
     * 是否超出了限制
     * @return
     */
    boolean isOver();

    boolean canStop();

}
