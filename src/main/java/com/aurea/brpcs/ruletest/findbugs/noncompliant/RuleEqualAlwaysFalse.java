package com.aurea.brpcs.ruletest.findbugs.noncompliant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RuleEqualAlwaysFalse {

    private String name;
    private String address;

    public void testRuleWithSet() {
        Set<RuleEqualAlwaysFalse> testSet = new HashSet<>();
        testSet.add(new RuleEqualAlwaysFalse());
    }

    public void testRuleWithMap() {
        Map<String, RuleEqualAlwaysFalse> testMap = new HashMap<>();
        testMap.put("test", new RuleEqualAlwaysFalse());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
