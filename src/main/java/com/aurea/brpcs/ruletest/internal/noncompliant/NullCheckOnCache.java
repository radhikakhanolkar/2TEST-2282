package com.aurea.brpcs.ruletest.internal.noncompliant;

import com.aurea.brpcs.ruletest.helpers.Cache;

public class NullCheckOnCache {

    public void testCacheCase(){

        Cache c = new Cache();

        System.out.println(c.getId());
        System.out.println(c.getName());

    }

}
