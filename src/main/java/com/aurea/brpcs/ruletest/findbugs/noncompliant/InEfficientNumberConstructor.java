package com.aurea.brpcs.ruletest.findbugs.noncompliant;

public class InEfficientNumberConstructor {

    public void testRule1() {
        Integer numberInt = new Integer(127);
        Long numberLong = new Long(-128);
        Integer numberInt2 = new Integer(128);
        Long numberLong2 = new Long(-129);
    }

    public void testRule2() {
        Integer numberInt = new Integer(126);
        Long numberLong = new Long(-127);
    }

    public void testRule3() {
        int numberIntPrim = 127;
        Integer numberInt = Integer.valueOf(numberIntPrim);
        long numberLongPrim = -128;
        Long numberLong = Long.valueOf(numberLongPrim);
    }

    public void testRule4() {
        int numberIntPrim = 128;
        Integer numberInt = new Integer(numberIntPrim);
        long numberLongPrim = -129;
        Long numberLong = new Long(numberLongPrim);
    }

}
