package com.aurea.brpcs.ruletest.squid.noncompliant;

public class S2444Rule {

	protected static Object instance = null;

	public static Object getInstance() {
		if (instance != null) {
			return instance;
		}

		instance = new Object(); // Noncompliant
		return instance;
	}
}
