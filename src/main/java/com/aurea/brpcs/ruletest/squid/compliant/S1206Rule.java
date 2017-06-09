package com.aurea.brpcs.ruletest.squid.compliant;

public class S1206Rule {

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
		// Noncompliant - should also override "hashCode()"
	}

	@Override
	public int hashCode() {
		return 12312;
	}
}
