package com.aurea.brpcs.ruletest.squid.compliant;

public final class S128Rule {

    public void check(int i) {
        switch(i) {
            case 0:
                if (System.currentTimeMillis() > 10) {
                    return;
                } else {
                    break;
                }
            case 1:
                System.out.println("abc");
                return;
            case 2:
                break;
            default:
        }
    }

    public void switchWithIf(String a) {
        switch(a) {
            case "x":
                if (a.length() > 5) {
                    if (toString().toLowerCase().equals("b")) {
                        return;
                    } else {
                        break;
                    }
                } else {
                    throw new IllegalStateException("x");
                }
            case "y":
                switch (a) {
                    case "b":
                        return;
                    default:
                }
                break;
            default:
                return;
        }
    }

}
