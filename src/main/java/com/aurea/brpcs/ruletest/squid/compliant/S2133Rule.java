package com.aurea.brpcs.ruletest.squid.compliant;

import com.aurea.brpcs.ruletest.helpers.Season;
import com.aurea.brpcs.ruletest.squid.noncompliant.*;
import java.util.Date;

public class S2133Rule {

    public void testGetClass(){
        Class c = Date.class;
    }

    public void test(Object obj) {
        if(Date.class == obj.getClass()){

        }
    }
}
