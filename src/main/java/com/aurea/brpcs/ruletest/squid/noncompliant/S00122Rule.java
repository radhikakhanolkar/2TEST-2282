package com.aurea.brpcs.ruletest.squid.noncompliant;

public final class S00122Rule {

    public void doSomething() {
        boolean someCondition = true;
        if(someCondition) doSomething();
    }

}
