package com.aurea.brpcs.ruletest.squid.noncompliant;

import com.aurea.brpcs.ruletest.squid.compliant.AbstractS1114Rule;

public class S1114Rule1 extends AbstractS1114Rule {

	@Override
	protected void finalize() { // Noncompliant; no call to super.finalize();
		releaseSomeResources();
	}

	public void releaseSomeResources() {

	}

}
