package com.aurea.brpcs.ruletest.squid.compliant;

public class S2444Rule {

    protected static Object instance = null;

    public static synchronized Object getInstance() {
        if (instance != null) {
            return instance;
        }

        instance = new Object(); // Noncompliant
        return instance;
    }
}
