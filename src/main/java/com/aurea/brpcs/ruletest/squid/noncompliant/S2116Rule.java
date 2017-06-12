package com.aurea.brpcs.ruletest.squid.noncompliant;

public class S2116Rule {

    public static void main(String[] args) {
        String argStr = args.toString(); // Noncompliant
        int argHash = args.hashCode(); // Noncompliant
    }
}
