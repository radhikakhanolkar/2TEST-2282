package com.aurea.brpcs.ruletest.squid.compliant;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class S2112Rule {

    public void checkUrl(URL url) throws URISyntaxException {
        Set<URI> sites = new HashSet<URI>();  // Compliant

        URI homepage = new URI("http://sonarsource.com");  // Compliant
        URI uri = url.toURI();
        if (homepage.equals(uri)) {  // Compliant
            // ...
        }
    }

}
