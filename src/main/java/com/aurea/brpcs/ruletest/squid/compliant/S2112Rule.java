package com.aurea.brpcs.ruletest.squid.compliant;

import com.aurea.brpcs.ruletest.helpers.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class S2112Rule {

    public void checkUrl(java.net.URL url) throws URISyntaxException {
        Set<URI> sites = new HashSet<URI>();  // Compliant
        Set<URL> urls = new HashSet<URL>();  // Compliant

        URI homepage = new URI("http://sonarsource.com");  // Compliant
        URI uri = url.toURI();
        if (homepage.equals(uri)) {  // Compliant
            // ...
        }
        URL helper = new URL();   // Compliant
        if (helper.equals("")){   // Compliant

        }
    }

}
