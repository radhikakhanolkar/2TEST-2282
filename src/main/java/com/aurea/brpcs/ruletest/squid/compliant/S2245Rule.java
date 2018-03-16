package com.aurea.brpcs.ruletest.squid.compliant;

import java.security.SecureRandom;
import com.aurea.brpcs.ruletest.helpers.squid.MySecureRandom;
import com.aurea.brpcs.ruletest.helpers.squid.Random;

public class S2245Rule {

    private final Random r = new Random(12);

    private final java.util.Random mySecureRandom = new MySecureRandom();

    public void doSomething() {
        java.util.Random random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
    }

}
