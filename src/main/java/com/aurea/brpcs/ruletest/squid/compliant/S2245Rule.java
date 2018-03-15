package com.aurea.brpcs.ruletest.squid.compliant;


import java.security.SecureRandom;
import java.util.Random;

public class S2245Rule {

    public void doSomething() {
        Random random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
    }

}