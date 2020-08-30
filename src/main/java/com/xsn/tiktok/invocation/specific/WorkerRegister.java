package com.xsn.tiktok.invocation.specific;

public interface WorkerRegister {

    void addWorker(Worker worker);

    void removeWorker(Worker worker);

    void changeWorker(Worker worker);
}
