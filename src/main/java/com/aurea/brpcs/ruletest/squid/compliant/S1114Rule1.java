package com.aurea.brpcs.ruletest.squid.compliant;

public class S1114Rule1 {

	protected void finalize() throws Throwable {
		releaseSomeResources();
		super.finalize(); //compliant- Super is at the end of the method.
	}

	public void releaseSomeResources() {

	}
}
