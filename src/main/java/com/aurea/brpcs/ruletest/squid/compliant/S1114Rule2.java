package com.aurea.brpcs.ruletest.squid.compliant;

public class S1114Rule2 {

	protected void finalize() throws Throwable {
		try {
			
		} catch (Throwable t) {
			releaseSomeResources();
			super.finalize(); //compliant- Super is at the end of the method.
		} 
	}

	public void releaseSomeResources() {

	}
}
