package com.aurea.brpcs.ruletest.squid.noncompliant;

public class ApparentInfiniteRecursiveLoop {

    public void checkCatchInterruptedException(){
        while(!Thread.interrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { //non-compliant
                Thread.currentThread().interrupt();
            }
        }
    }
}
