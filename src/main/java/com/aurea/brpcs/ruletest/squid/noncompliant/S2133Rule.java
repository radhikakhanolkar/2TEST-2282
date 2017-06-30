package com.aurea.brpcs.ruletest.squid.noncompliant;

import java.util.Date;

public class S2133Rule {

    Date date = new Date();

    public void testGetClass() {
        Class d = new Date().getClass();
        Class c = date.getClass();
    }

}
