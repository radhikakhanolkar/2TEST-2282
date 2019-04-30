package com.aurea.brpcs.ruletest.squid.noncompliant;

import com.aurea.brpcs.ruletest.squid.compliant.MyLock;

public class S2222Rule2 extends MyLock {

    public void doSomething() {
        super.lock();
        if (isInitialized()) {
            System.out.println("in doSomethingMethod");
            super.unlock();//non-compliant
        }
    }

    public boolean isInitialized() {
        return true;
    }

}
