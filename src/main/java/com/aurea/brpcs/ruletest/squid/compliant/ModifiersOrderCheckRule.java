package com.aurea.brpcs.ruletest.squid.compliant;

public class ModifiersOrderCheckRule {

    public static void main(String[] args) {
    }

    public final Integer case1() {
        return 1;
    }

    private static final String CASE2 = "Case2";

    protected static synchronized Integer case3() {
        return 2;
    }

}
