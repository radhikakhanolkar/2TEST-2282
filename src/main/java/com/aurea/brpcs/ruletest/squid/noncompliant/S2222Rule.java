package com.aurea.brpcs.ruletest.squid.noncompliant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class S2222Rule {
	private Lock lock = new ReentrantLock();

	public void doSomething() {
		lock.lock();
		if (isInitialized()) {
			System.out.println("in doSomethingMethod");
			lock.unlock();//non-compliant
		}
	}

	public boolean isInitialized() {
		return true;
	}
}
