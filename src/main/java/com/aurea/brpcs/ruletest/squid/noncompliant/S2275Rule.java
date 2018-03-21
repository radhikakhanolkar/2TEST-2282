package com.aurea.brpcs.ruletest.squid.noncompliant;

import java.text.MessageFormat;

public class S2275Rule {
	private void aNonComplaintMethod() {

		int c=2011;
		int value = 99;
		Object unUsedObject = null;

		String.format("The value of my integer is %d", "Hello World");
		String.format("Duke's Birthday year is %tX", c);
		String.format("Display %0$d and then %d", 1);
		String.format("Not enough arguments %d and %d", 1);
		String.format("%< is equals to %d", 2);

		MessageFormat.format("Result {1}.", value);
		MessageFormat.format("Result {{0}.", value);

		MessageFormat.format("Result ' {0}", value);
	}

	private void aComplaintOne() {
		int c=2011;
		int value = 99;
		Object myObject = null;
		
		String.format("The value of my integer is %d", 3);
		String.format("Duke's Birthday year is %tY", c);
		String.format("Display %1$d and then %d", 1);
		String.format("Not enough arguments %d and %d", 1, 2);
		String.format("%d is equals to %<", 2);

		MessageFormat.format("Result {0}.", value);
		MessageFormat.format("Result {0} & {1}.", value, value);
		MessageFormat.format("Result {0}.", myObject);
	}

	public void duh() {
		int i = -99;
		this.aNonComplaintMethod();  //Some Comment
		this.aComplaintOne();
		
		String.format("%d",  i);
	}
}
