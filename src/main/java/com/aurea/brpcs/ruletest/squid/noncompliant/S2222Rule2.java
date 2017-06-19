package com.aurea.brpcs.ruletest.squid.noncompliant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class S2222Rule2 {
	private Lock lock = new ReentrantLock();

	public void doSomething() {
		lock.lock();
		try {
			System.out.println("in doSomethingMethod");
		} catch (Throwable t) {
			lock.unlock(); //non-compliant
		}
	}

	public boolean isInitialized() {
		return true;
	}
}
