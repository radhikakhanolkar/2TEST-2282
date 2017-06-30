package com.aurea.brpcs.ruletest.findbugs.noncompliant;

public class InEfficientNumberConstructor {

    public void testRule1() {

        Integer numberInt = new Integer(127);
        Long numberLong = new Long(-128);
    }

    public void testRule2() {

        Integer numberInt = new Integer(126);
        Long numberLong = new Long(-127);
    }

    public void testRule3() {

        int numberIntPrim = 127;
        Integer numberInt = new Integer(numberIntPrim);
        long numberLongPrim = -128;
        Long numberLong = new Long(numberLongPrim);
    }
}
