package com.aurea.brpcs.ruletest.squid.noncompliant;

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
			return;                                        // Non-Compliant - prevents the RuntimeException from being propagated
		}
	}
}
