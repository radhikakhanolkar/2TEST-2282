package com.aurea.brpcs.ruletest.findbugs.noncompliant;

public class InEfficientNumberConstructor {

    public void testRule1() {
        Integer numberInt = new Integer(127); //
        Long numberLong = new Long(-128); //
    }

    public void testRule2() {
        Integer numberInt = new Integer(126); //
        Long numberLong = new Long(-127); //
    }

    public void testRule3() {
        int numberIntPrim = 128;
        Integer numberInt = new Integer(numberIntPrim); //
        long numberLongPrim = -129;
        Long numberLong = new Long(numberLongPrim); //
    }

    public Integer testRule4() {
        return new Integer(0); //
    }

    public String testRule5(int myInt) {
        return new Integer(myInt).toString(); //
    }

    public int testRule6(String myString) {
        return new Integer(myString).intValue(); //
    }

    public Integer testRule7(String myString) {
        return new Integer(myString); //
    }

    public void testRule8(){
        Integer numberInt2 = new Integer(128);//
        Long numberLong2 = new Long(-129);//
    }

}
