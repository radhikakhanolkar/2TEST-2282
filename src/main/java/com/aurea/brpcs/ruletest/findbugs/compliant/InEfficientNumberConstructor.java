package com.aurea.brpcs.ruletest.findbugs.compliant;

public class InEfficientNumberConstructor {

    public void testRule1() {
        Integer numberInt = Integer.valueOf(127);
        Long numberLong = Long.valueOf(-128);
    }

}
