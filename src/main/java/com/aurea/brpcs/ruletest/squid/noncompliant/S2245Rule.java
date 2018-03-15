package com.aurea.brpcs.ruletest.squid.noncompliant;

import java.util.Random;

public class S2245Rule {

    private static final Random myGenerator = new Random(System.currentTimeMillis());

    private Random r = new java.util.Random();

    public int something() {
        return (int) Math.random();
    }

}
