package com.aurea.brpcs.ruletest.squid.noncompliant;

public class S1114Rule1 {

	@Override
	protected void finalize() { // Noncompliant; no call to super.finalize();
		releaseSomeResources();
	}

	public void releaseSomeResources() {

	}

}
