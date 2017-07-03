package com.aurea.brpcs.ruletest.squid.compliant;

public class S2245Rule {

    private static class Random {
        private final int value;

        private Random(int value) {
            this.value = value;
        }

    }

    private final Random r = new Random(12);

}
