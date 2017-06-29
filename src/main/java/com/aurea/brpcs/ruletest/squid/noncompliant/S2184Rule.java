package com.aurea.brpcs.ruletest.squid.noncompliant;

import java.util.Date;

public class S2184Rule {

    // * - cases with CG tree issues

    float twoThirdsFloat = 2 / 3; // CASE A - Noncompliant; int division. Yields 0.0
    float multiFloat = 2 * 3; // CASE A
    double twoThirdsDouble = 2 / 3; // CASE A - Noncompliant; int division. Yields 0.0
    double multiDouble = 2 * 3; //CASE A
    long millisInYear = 1_000 * 3_600 * 24 * 365; // CASE C - Noncompliant; int multiplication. Yields 1471228928
    long bigNum = Integer.MAX_VALUE + 2; // CASE D - Noncompliant. Yields -2147483647
    long bigNegNum = Integer.MIN_VALUE - 1; //CASE D - Noncompliant, gives a positive result instead of a negative one.
    int intNum = 1000;
    long bigNum2 = intNum + 2;

    long seconds = 2147483;
    Date myDate = new Date(seconds * 1_000); //Noncompliant, won't produce the expected result if seconds > 2_147_483_647

    long testCast = 60*1000 + 1; //CASE C
    long testCast2 = 60*1000 - 1; //CASE C

    public long compute(int factor) {
        return factor * 10_000;  //* Noncompliant, won't produce the expected result if factor > 214_748
    }

    public float compute2(long factor) {
        return factor / 123;  //* CASE B - Noncompliant, will be rounded to closest long integer
    }

    public double compute3(long factor) {
        return factor / 123;  //* CASE B - Noncompliant, will be rounded to closest long integer
    }

    public void compute4(){
        voidDoubleMethod(2/3); //*
        voidFloatMethod(2/3); //*
        voidLongMethod(1_000 * 3_600 * 24 * 365); //*
        voidLongMethod(Integer.MAX_VALUE + 2); //*
        voidLongMethod(Integer.MIN_VALUE - 1); //*
    }

    public void voidDoubleMethod (double val) {

    }

    public void voidFloatMethod (float val) {

    }

    public void voidLongMethod (long val) {

    }

}
