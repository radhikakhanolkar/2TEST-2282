package com.aurea.brpcs.ruletest.squid.compliant;

import java.util.Date;

public class S2133Rule {

    public void testGetClass() {
        Class c = Date.class;
    }

    public void test(Object obj) {
        if (Date.class == obj.getClass()) {

        }

        Date date1 = new Date();
        date1.setTime(1000L);
        if(date1.getClass() == Date.class){
            testMethodDate(date1);
        }
    }

    public void testMethodDate(Date date){
        System.out.println(date);
    }
}
