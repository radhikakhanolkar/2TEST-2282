@Grab('com.opencsv:opencsv:3.9')
@Grab('com.sun.jersey:jersey-bundle:1.19.3')
@Grab('org.json:json:20170516')
@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7')
@Grab('mysql:mysql-connector-java:5.1.39')
@GrabConfig(systemClassLoader = true)

import groovyx.net.http.Method
import groovyx.net.http.RESTClient

/**
 * Created by ajanoni on 08/06/17.
 *
 * ####################################################################
 * !!!!! IMPORTANT !!!!!
 * If you are running it from IntelliJ:
 * Alt+Enter with a caret positioned on @Grab to download artifacts.
 * ####################################################################
 *
 */

cgProjects = [
        'https://github.com/apache/maven'                                    : 'master',
        'https://github.com/BangKindo/Beginner'                              : 'master',
        'https://github.com/bitcoinj/bitcoinj'                               : 'master',
        'https://github.com/EasyRules/easyrules'                             : 'master',
        'https://github.com/ktuukkan/marine-api'                             : 'master',
        'https://github.com/rajithd-aurea/rest-api-violation'                : 'master',
        'https://github.com/spring-projects/aws-maven'                       : 'master',
        'https://github.com/square/okhttp'                                   : 'master',
        'https://github.com/TetianaMalva/begin'                              : 'master',
        'https://github.com/trilogy-group/aLine-FirewalForCode'              : 'master',
        'https://github.com/trilogy-group/aurea-dxsi'                        : 'master',
        'https://github.com/trilogy-group/aurea-ipm-main'                    : 'master',
        'https://github.com/trilogy-group/aurea-sonic-mq'                    : 'master',
        'https://github.com/trilogy-group/bc-java.git'                       : 'master',
        'https://github.com/trilogy-group/devfactory-docker-scorecard'       : 'development',
        'https://github.com/trilogy-group/devfactory-utbelt'                 : 'develop',
        'https://github.com/trilogy-group/kerio-connect-connect'             : 'master',
        'https://github.com/trilogy-group/kerio-winroute-at-mykerio'         : 'master',
        'https://github.com/trilogy-group/QuickSearch-demo'                  : 'master',
        'https://github.com/trilogy-group/versata-m1.client-ems-client'      : 'master',
        'https://scm-ba.devfactory.com/scm/cap/business-avios'               : 'master',
        'https://scm-ba.devfactory.com/scm/cap/business-framework'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap/business-redemptions-builders': 'master',
        'https://scm-ba.devfactory.com/scm/cap/reservations-common'          : 'master',
        'https://scm-ba.devfactory.com/scm/cap/web-contextualisation'        : 'master',
        'https://scm-ba.devfactory.com/scm/cap/web-payment'                  : 'master',
        'https://scm-ba.devfactory.com/scm/cap/web-selling-builders'         : 'master',
        'https://scm-ba.devfactory.com/scm/cap/web-sitenavigation'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/fc-baflt-bafma'              : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/fc-ndc-cma'                  : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/fc-pym-vpa'                  : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/fc-sea-sesa'                 : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/mp-evm-evtm'                 : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-aaui-cpm'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-acd-emdm'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-asm-ema'            : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-baflt-bamsr'        : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-bds-mdp'            : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-car-cav'            : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-cargopub-cargopub'  : 'Master_15April2017',
        'https://scm-ba.devfactory.com/scm/cap2/services-cem-cpr'            : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-cmg-ccb'            : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-concur-tem'         : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-core-ehcache'       : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-crw-ccbt'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-dvm-tcm'            : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-fli-cbu'            : 'Master_15April2017',
        'https://scm-ba.devfactory.com/scm/cap2/services-fom-foma'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-iata-ssba'          : 'Master_15April2017',
        'https://scm-ba.devfactory.com/scm/cap2/services-meo-lca'            : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-ndc-dist'           : 'Master_15April2017',
        'https://scm-ba.devfactory.com/scm/cap2/services-orm-ordrt'          : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-pega-cma'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-sas-mfas'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-sse-invm'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-sse-sbkm'           : 'master',
        'https://scm-ba.devfactory.com/scm/cap2/services-svrm-manc'          : 'master',
        'https://scm.devfactory.com/stash/scm/crossover/bandcamp'            : 'develop',
        'https://scm.devfactory.com/stash/scm/messageone/m1-ems'             : 'master'
]

String.metaClass.encodeURL = {
    java.net.URLEncoder.encode(delegate, "UTF-8")
}

println ">>> SCRIPT STARTED <<<"
int count = 15957;
cgProjects.each { sourceUrl, branch ->
    count++;
    retry(1, { e -> e.printStackTrace() }) {
        def urlCS = sourceUrl + ".git?branch=" + branch
        def finalUrlCS = "http://codeserver.devfactory.com/api/v2/repositories/"+count+"?dfScmUrl="+urlCS.encodeURL()+"&replayHistoricCommits=true";
        println finalUrlCS;
        def cgClient = new RESTClient(finalUrlCS);
        cgClient.getClient().params.setParameter("http.connection.timeout", 10000)
        cgClient.getClient().params.setParameter("http.socket.timeout", 11000)

                cgClient.request(Method.PUT) {
                    response.success = { resp, json ->
                        println "OK - " + sourceUrl;
                    }

                    response.failure = { resp ->
                        println "Unexpected error: ${resp.statusLine.statusCode}"
                        println $ { resp.statusLine.reasonPhrase }
                    }
                }
    }
}

def retry(int times = 5, Closure errorHandler = { e -> log.warn(e.message, e) }
          , Closure body) {
    int retries = 0
    def exceptions = []
    while (retries++ < times) {
        try {
            if (retries > 1) {
                println ">>> Attempt:" + retries
            }
            return body.call()
        } catch (e) {
            exceptions << e
            errorHandler.call(e)
        }
    }
    throw new Exception(">>> Failed after $times retries")
}
