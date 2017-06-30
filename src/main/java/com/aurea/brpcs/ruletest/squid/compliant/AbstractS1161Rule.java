package com.aurea.brpcs.ruletest.squid.compliant;

public abstract class AbstractS1161Rule implements S1161Interface{
	
	protected abstract void someMethod(String aParam);

	public void doOneThing() {
		//abstract class, doesn't need @Override.
	}

	
	
}
