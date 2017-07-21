package com.aurea.brpcs.ruletest.findbugs.compliant;

public class REC_CATCH_EXCEPTION {

    private void resolve(Class c) {
        try {
            c.getGenericSuperclass();
            c.getGenericInterfaces();
        } catch (RuntimeException x) {
            throw x;
        } catch (Exception x) {
            throw (LinkageError) new LinkageError("Failed to resolve " + c).initCause(x);
        }
    }
}
