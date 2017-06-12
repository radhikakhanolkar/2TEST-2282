package com.aurea.brpcs.ruletest.squid.compliant;

import java.util.Arrays;

public class S2116Rule {

    public static void main(String[] args) {
        String argStr = Arrays.toString(args);
        int argHash = Arrays.hashCode(args);
    }
}
