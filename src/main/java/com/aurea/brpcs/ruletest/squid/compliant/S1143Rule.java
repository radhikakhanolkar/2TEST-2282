package com.aurea.brpcs.ruletest.squid.compliant;

public class S1143Rule {

    public static void main(String[] args) {
        try {
            doSomethingWhichThrowsException();
            System.out.println("OK");
        } catch (RuntimeException e) {
            System.out.println("ERROR");
        }
    }


    public static void doSomethingWhichThrowsException() {
        try {
            throw new RuntimeException();
        } finally {
            System.out.println("FINALLY OK");
        }
    }
}
