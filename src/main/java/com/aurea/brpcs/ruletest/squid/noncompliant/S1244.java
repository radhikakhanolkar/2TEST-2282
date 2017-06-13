package com.aurea.brpcs.ruletest.squid.noncompliant;


public class S1244 {

    public void testMethod() {

        float myNumber = (float) 3.146;
        if (myNumber == 3.146f) {
        }

        if (myNumber != 3.146f) { //Noncompliant. Because of floating point imprecision, this will be true

        }


        if (myNumber < 4 || myNumber > 4) { // Noncompliant; indirect inequality test

        }

        float f = 0;
        double d = 0;
        if (f != d) { // Noncompliant

        }

        if (getFloatValue1() == getFloatValue2()) {

        }

        if (getFloatValue1() != getFloatValue2()) {

        }


    }

    private float getFloatValue1() {
        return 1.0f;
    }

    private float getFloatValue2() {
        return 1.0f;
    }
}
