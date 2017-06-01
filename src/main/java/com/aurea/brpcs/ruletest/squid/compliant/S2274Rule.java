package com.aurea.brpcs.ruletest.squid.compliant;

public class S2274Rule {

    public void waitTest() throws InterruptedException {
        Integer obj = 1;
        synchronized (new Object()) {
            while (obj != 2) {
                obj++;
                obj.wait(1000); // the thread can wakeup whereas the condition
                // is still false
            }

        }
    }

    public void waitAnotherOkTest() throws InterruptedException {
        Integer obj = 1;
        synchronized (new Object()) {
            do {
                obj++;
                obj.wait(1000);
            } while (obj != 2);
        }
    }

}