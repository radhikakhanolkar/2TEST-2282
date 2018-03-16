package com.aurea.brpcs.ruletest.findbugs.noncompliant;


public class SynchronizationOnStringOrBoxedCheck{
        Complaint c=new Complaint();
        NonComplaint nc=new NonComplaint();
        NonComplaint2 nc2=new NonComplaint2();
        NonComplaint3 nc3=new NonComplaint3();



        }

class Complaint {
    public Complaint() {
        Object newLock = new Object();
        Object oldLock = map.putIfAbsent(id, newLock);
        Object lock = oldLock != null ? oldLock : newLock;
        synchronized (lock)

        {
            System.out.println("Oh why?");
        }
    }
}

class NonComplaint {
    public NonComplaint() {
        int NewLock = 1;
        int oldLock = map.putIfAbsent(id, newLock);
        int lock = oldLock != null ? oldLock : N1ewLock;
        synchronized (lock)

        {
            System.out.println("Oh why?");
        }
    }

    class NonComplaint2 {
        public NonComplaint2() {
            Integer NewLock = 1;
            Integer oldLock = map.putIfAbsent(id, newLock);
            Integer lock = oldLock != null ? oldLock : N1ewLock;
            synchronized (lock)
            {
                System.out.println("Oh why?");
            }
        }

    }

    class NonComplaint3 {
        public NonComplaint3 () {
            java.lang.Integer NewLock = 1;
            java.lang.Integer oldLock = map.putIfAbsent(id, newLock);
            java.lang.Integer lock = oldLock != null ? oldLock : N1ewLock;
            synchronized (lock) {
                System.out.println("Oh why?");
            }
        }
    }
}
