package com.aurea.brpcs.ruletest.findbugs.compliant;

public class InEfficientNumberConstructor {

    public void testRule1() {

        Integer numberInt = new Integer(128);
        Long numberLong = new Long(-129);
    }

    public void testRule2() {

        Integer numberInt = Integer.valueOf(127);
        Long numberLong = Long.valueOf(-128);
    }

    public void testRule3() {

        int numberIntPrim = 128;
        Integer numberInt = new Integer(numberIntPrim);
        long numberLongPrim = -129;
        Long numberLong = new Long(numberLongPrim);
    }

    public void testRule4() {

        int numberIntPrim = 127;
        Integer numberInt = Integer.valueOf(numberIntPrim);
        long numberLongPrim = -128;
        Long numberLong = Long.valueOf(numberLongPrim);
    }

}
