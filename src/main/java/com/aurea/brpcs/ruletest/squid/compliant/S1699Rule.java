package com.aurea.brpcs.ruletest.squid.compliant;

public class S1699Rule {

    public S1699Rule() {
        doSomething();  // Noncompliant
    }

    private void doSomething() {  // not final; can be overridden
    }

}
