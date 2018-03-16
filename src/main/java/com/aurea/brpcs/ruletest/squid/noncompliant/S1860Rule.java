package com.aurea.brpcs.ruletest.findbugs.noncompliant;


public class S1860Rule{

    public void simpleCalls(){
        complaint();
        nonComplaint();
        nonComplaint2();
        nonComplaint3();
    }

    private void complaint() {
        Object lock;
        synchronized (lock)
        {
            System.out.println("complaint()");
        }
    }

    void nonComplaint() {
        int lock;
        synchronized (lock)
        {
            System.out.println("nonComplaint()");
        }
    }

    public void nonComplaint2() {
            Integer lock;
            synchronized (lock)
            {
                System.out.println("nonComplaint2()");
            }
        }


    public void nonComplaint3 () {
            java.lang.Integer lock;
            synchronized (lock) {
                System.out.println("nonComplaint3()");
            }
        }
}
