package com.aurea.brpcs.ruletest.squid.compliant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class S2222Rule2 {
	private Lock lock = new ReentrantLock();

	public void doSomething() {

		try {
			lock.lock();
			System.out.println("in doSomethingMethod");
		} catch (Throwable t) {
			System.err.println("Error in doSomethingMethod");
		} finally {
			lock.unlock();
		}

	}

	public boolean isInitialized() {
		return true;
	}
}
