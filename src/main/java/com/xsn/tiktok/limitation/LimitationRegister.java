package com.xsn.tiktok.limitation;

public interface LimitationRegister {

    void addLimitation(Limitation limitation);

    void removeLimitation(Limitation limitation);

    void changeLimitation(Limitation limitation);
}
