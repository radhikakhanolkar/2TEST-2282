package com.aurea.brpcs.ruletest.squid.noncompliant;

public class S1114Rule2 {

	@Override
	protected void finalize() throws Throwable {
		super.finalize(); // Noncompliant; this call should come last
		releaseSomeResources();
	}

	public void releaseSomeResources() {

	}

}
