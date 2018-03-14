package com.aurea.brpcs.ruletest.squid.compliant;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class S2236Rule {

    private static final int TIMEOUT = 2000;

    public void testThreadWait() throws InterruptedException {
        Thread myThread = new Thread();
        Integer x = 1;
        synchronized (new Object()) {
            while (x != 2) {
                x++;
                x.wait(2000);
            }
        }
        myThread.start();
    }

    public void testThreadWait_Case2() throws InterruptedException {
        Thread myThread = new Thread();
        Integer x = 1;
        synchronized (new Object()) {
            while (x != 2) {
                x++;
                x.wait(TIMEOUT);
            }
        }
        myThread.start();
    }

    public void testCountDownLatch() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        startSignal.await(1, TimeUnit.MINUTES);
    }

    public void testNonInheritedWait() {
        WaitTest wait = new WaitTest();
        wait.wait("None");
    }

    class WaitTest {

        public void wait(String param) {
            doNothing();
        }

        private void doNothing() {
            System.out.println("doNothing");
        }
    }
}
