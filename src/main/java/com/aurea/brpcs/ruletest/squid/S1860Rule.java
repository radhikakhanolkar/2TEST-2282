package com.aurea.brpcs.ruletest.squid.noncompliant;


public class S1860Rule{

    public void simpleCalls(){
        complaint();
        nonComplaint1();
        nonComplaint2();
    }

    private void complaint() {
        Object lock=null;
        synchronized (lock)
        {
            System.out.println("complaint()");
        }
    }


    public void nonComplaint1() {
            Integer lock=1;
            synchronized (lock)
            {
                System.out.println("nonComplaint2()");
            }
        }


    public void nonComplaint2 () {
            java.lang.Integer lock=1;
           synchronized (lock) {
                System.out.println("nonComplaint3()");
            }
        }
}