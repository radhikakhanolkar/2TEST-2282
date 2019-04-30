package com.aurea.brpcs.ruletest.squid.noncompliant;

public class ObjectFinalizeCheckRule {

    public void dispose() throws Throwable {
        this.finalize();                       // Noncompliant
    }

}
