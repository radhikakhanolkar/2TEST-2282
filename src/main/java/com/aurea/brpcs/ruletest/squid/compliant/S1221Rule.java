package com.aurea.brpcs.ruletest.squid.compliant;

public class S1221Rule {
    public static final class WithPublicHashcodeMethod {
        public int HashCode() {
            return 0;
        }
        public int HaShcOde() {
            return 0;
        }
        public int HASHCODE() {
            return 0;
        }
        public long hashCODE() {
            return 0;
        }
    }

    public static final class WithPrivateHashcodeMethod {
        private int HashCode() {
            return 0;
        }
        private int HaShcOde() {
            return 0;
        }
        private int HASHCODE() {
            return 0;
        }
        private long hashCODE() {
            return 0;
        }
    }

    public static final class WithProtectedHashcodeMethod {
        protected int HashCode() {
            return 0;
        }
        protected int HaShcOde() {
            return 0;
        }
        protected int HASHCODE() {
            return 0;
        }
        protected long hashCODE() {
            return 0;
        }
    }

    public static final class WithDefaultHashcodeMethod {
        protected int HashCode() {
            return 0;
        }
        protected int HaShcOde() {
            return 0;
        }
        protected int HASHCODE() {
            return 0;
        }
        protected long hashCODE() {
            return 0;
        }
    }
}
