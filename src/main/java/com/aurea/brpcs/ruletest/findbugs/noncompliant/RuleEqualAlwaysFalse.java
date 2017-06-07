package com.aurea.brpcs.ruletest.findbugs.noncompliant;

public class RuleEqualAlwaysFalse {

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
