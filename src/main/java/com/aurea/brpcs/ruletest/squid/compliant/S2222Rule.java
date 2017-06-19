package com.aurea.brpcs.ruletest.squid.compliant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class S2222Rule {
	private Lock lock = new ReentrantLock();

	public void doSomething() {
		if (isInitialized()) { // Noncompliant
			lock.lock();
			System.out.println("in doSomethingMethod");
			lock.unlock();
		}
	}

	public boolean isInitialized() {
		return true;
	}
}
