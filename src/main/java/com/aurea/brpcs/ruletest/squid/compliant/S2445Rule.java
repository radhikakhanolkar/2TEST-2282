package com.aurea.brpcs.ruletest.squid.compliant;

public class S2445Rule {

	private String color = "red";
	private final Object lockObj = new Object();

	private void doSomething() {
		synchronized (lockObj) { // compliant; 
	    color = "green";
	  }
	}

	private void doOtherThing(final String colorParam) {
		synchronized (colorParam) { // compliant; 
			color = "green"; // other threads now allowed into this block
		}
	}
}
