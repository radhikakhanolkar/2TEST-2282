package com.aurea.brpcs.ruletest.squid.compliant;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class S2274Rule {

    private static final int TIMEOUT = 2000;

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
