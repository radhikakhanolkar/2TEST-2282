package com.aurea.brpcs.ruletest.squid.noncompliant;

public class S1143Rule {

	public static void main(String[] args) {
		try {
			case1();
			System.out.println("OK");
		} catch (RuntimeException e) {
			System.out.println("ERROR");
		}
	}

	public static void case1() {
		try {
			throw new RuntimeException();
		} finally {
			return;                                        // Non-Compliant - prevents the RuntimeException from being propagated
		}
	}


	public static void case2() {
		try {
			throw new RuntimeException();
		} finally {
			throw new Exception()                          // Non-Compliant - prevents the RuntimeException from being propagated
		}
	}

	public static void case3() {
		while(x<10) {
			try {
				x++;
				throw new RuntimeException();
			} finally {
				continue;                          // Non-Compliant - prevents the RuntimeException from being propagated
			}
		}
	}

	public static void case4() {
		while(x<10) {
			try {
				x++;
				throw new RuntimeException();
			} finally {
				break;                          // Non-Compliant - prevents the RuntimeException from being propagated
			}
		}
	}
}
