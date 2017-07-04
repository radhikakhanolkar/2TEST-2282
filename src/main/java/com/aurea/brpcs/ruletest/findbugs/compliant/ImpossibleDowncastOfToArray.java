package com.aurea.brpcs.ruletest.findbugs.compliant;

import java.util.Collection;

public class ImpossibleDowncastOfToArray {

    String[] getAsArray(Collection c) {
        return (String[])c.toArray(new String[c.size()]);
    }
}
