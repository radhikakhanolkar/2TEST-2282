import groovyx.net.http.ContentType
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
//        'https://github.com/apache/maven'                                              : '',
//        'https://github.com/BangKindo/Beginner'                                        : '',
//        'https://github.com/bitcoinj/bitcoinj'                                         : '',
//        'https://github.com/DevFactory/codegraph_it_java'                              : '',
//        'https://github.com/EasyRules/easyrules'                                       : '',
//        'https://github.com/gruntjs/grunt-contrib-jshint'                              : '',
//        'https://github.com/jasmine/jasmine'                                           : '',
//        'https://github.com/JoshClose/CsvHelper'                                       : '',
//        'https://github.com/ktuukkan/marine-api'                                       : '',
//        'https://github.com/madskristensen/MiniBlog'                                   : '',
//        'https://github.com/mbdavid/LiteDB'                                            : '',
//        'https://github.com/mgravell/protobuf-net'                                     : '',
//        'https://github.com/NancyFx/Nancy'                                             : '',
//        'https://github.com/nhibernate/nhibernate-core'                                : '',
//        'https://github.com/nopSolutions/nopCommerce'                                  : '',
//        'https://github.com/paulcbetts/ModernHttpClient'                               : '',
//        'https://github.com/pixijs/pixi.js'                                            : '',
//        'https://github.com/ponsonio-aurea/Leaflet'                                    : '',
//        'https://github.com/pranet/cs_sample'                                          : '',
//        'https://github.com/rajithd-aurea/rest-api-violation'                          : '',
//        'https://github.com/restsharp/RestSharp'                                       : '',
//        'https://github.com/schambers/fluentmigrator'                                  : '',
//        'https://github.com/SignalR/SignalR'                                           : '',
//        'https://github.com/spring-projects/aws-maven'                                 : '',
//        'https://github.com/square/okhttp'                                             : '',
//        'https://github.com/TetianaMalva/begin'                                        : '',
//        'https://github.com/trilogy-group/aLine-FirewalForCode'                        : '',
//        'https://github.com/trilogy-group/aurea-ace-generix'                           : '',
//        'https://github.com/trilogy-group/aurea-crm-aline'                             : '',
//        'https://github.com/trilogy-group/aurea-crm-office-addin'                      : '',
//        'https://github.com/trilogy-group/aurea-dxsi'                                  : '',
//        'https://github.com/trilogy-group/aurea-ipm-main'                              : '',
//        'https://github.com/trilogy-group/aurea-nextdocs-adlib'                        : '',
//        'https://github.com/trilogy-group/aurea-sonic-mq'                              : '',
//        'https://github.com/trilogy-group/bc-java.git'                                 : '',
//        'https://github.com/trilogy-group/devfactory-acme'                             : '',
//        'https://github.com/trilogy-group/devfactory-docker-scorecard'                 : '',
//        'https://github.com/trilogy-group/devfactory-ideplugins2.0-visualstudio-plugin': '',
//        'https://github.com/trilogy-group/devfactory-utbelt'                           : '',
//        'https://github.com/trilogy-group/gfi-eventsmanager'                           : '',
        'https://github.com/trilogy-group/gfi-oneguard'                                : '',
//        'https://github.com/trilogy-group/ignite-acorn-eps'                            : '',
//        'https://github.com/trilogy-group/jenkins/tree/master'                         : '',
//        'https://github.com/trilogy-group/kerio-connect-connect'                       : '',
//        'https://github.com/trilogy-group/kerio-winroute-at-mykerio'                   : '',
//        'https://github.com/trilogy-group/QuickSearch-demo'                            : '',
//        'https://github.com/trilogy-group/versata-epmlive-epml-c2'                     : '',
//        'https://github.com/trilogy-group/versata-m1.client-ems-client'                : '',
//        'https://scm-ba.devfactory.com/scm/cap/business-avios'                         : '',
//        'https://scm-ba.devfactory.com/scm/cap/business-framework'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap/business-redemptions-builders'          : '',
//        'https://scm-ba.devfactory.com/scm/cap/business-stafftravel'                   : '',
//        'https://scm-ba.devfactory.com/scm/cap/reservations-common'                    : '',
//        'https://scm-ba.devfactory.com/scm/cap/web-contextualisation'                  : '',
//        'https://scm-ba.devfactory.com/scm/cap/web-diagtool'                           : '',
//        'https://scm-ba.devfactory.com/scm/cap/web-framework-schema'                   : '',
//        'https://scm-ba.devfactory.com/scm/cap/web-payment'                            : '',
//        'https://scm-ba.devfactory.com/scm/cap/web-selling-builders'                   : '',
//        'https://scm-ba.devfactory.com/scm/cap/web-sitenavigation'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap/web-xmlhttp-proxy'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/fc-baflt-bafma'                        : '',
//        'https://scm-ba.devfactory.com/scm/cap2/fc-ndc-cma'                            : '',
//        'https://scm-ba.devfactory.com/scm/cap2/fc-pym-vpa'                            : '',
//        'https://scm-ba.devfactory.com/scm/cap2/fc-sea-sesa'                           : '',
//        'https://scm-ba.devfactory.com/scm/cap2/mp-evm-evtm'                           : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-aaui-cpm'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-acd-emdm'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-asm-ema'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-baflt-bamsr'                  : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-bds-mdp'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-car-cav'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-cargopub-cargopub'            : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-cem-cpr'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-cmg-ccb'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-concur-tem'                   : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-core-ehcache'                 : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-crw-ccbt'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-dvm-tcm'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-fli-cbu'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-fom-foma'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-iata-ssba'                    : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-meo-lca'                      : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-ndc-dist'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-orm-ordrt'                    : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-pega-cma'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-sas-mfas'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-sse-invm'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-sse-sbkm'                     : '',
//        'https://scm-ba.devfactory.com/scm/cap2/services-svrm-manc'                    : '',
//        'https://scm.devfactory.com/stash/scm/adfp-autofix/autofix-backend'            : '',
//        'https://scm.devfactory.com/stash/scm/aurea-dfi/dotnet-importer'               : '',
//        'https://scm.devfactory.com/stash/scm/bug-prediction/bugspot'                  : '',
//        'https://scm.devfactory.com/stash/scm/crossover/bandcamp'                      : '',
//        'https://scm.devfactory.com/stash/scm/dfapi/codeserver'                        : '',
//        'https://scm.devfactory.com/stash/scm/easycover/backend.net'                   : '',
//        'https://scm.devfactory.com/stash/scm/messageone/m1-ems'                       : '',
//        'https://scm.devfactory.com/stash/scm/strongtest/strong-test-dot-net-plugin'   : '',
        'https://scm.devfactory.com/stash/scm/upland-eclipse/eclipse'                  : ''
//        'https://scm.devfactory.com/stash/scm/upland-filebound/leadlander'             : '',
//        'https://scm.devfactory.com/stash/scm/weaktests/backend'                       : ''
]



println ">>> SCRIPT STARTED <<<"

urlCG = 'http://codegraph-api-prod.ecs.devfactory.com/api/1.0/graphs?status=Completed&active=true'

println ">>> URL CODEGRAPH: " + urlCG
println ">>> GMT:" + new Date().toGMTString()

def cgClient = new RESTClient(urlCG)
cgClient.getClient().params.setParameter("http.connection.timeout", 10000)
cgClient.getClient().params.setParameter("http.socket.timeout", 11000)

cgProjects.each { sourceUrl, revision ->
    retry(1, { e -> e.printStackTrace() }) {
        cgClient.request(Method.GET) {
            response.success = { resp, json ->
                processResponse(json, sourceUrl, revision)
            }

            response.failure = { resp ->
                println "Unexpected error: ${resp.statusLine.statusCode}"
                println $ { resp.statusLine.reasonPhrase }
            }
        }
    }
}


private void processResponse(json, sourceUrl, revision) {
    def ret = json.findAll {
        it.find { key, value ->
            value == sourceUrl
        } &&
                it.find { key, value ->
                    value == revision
                }
    }

    if (ret.size() == 0) {
        def cgClientPOST = new RESTClient('https://codegraph-api-prod.ecs.devfactory.com/api/1.0/graphs')
        cgClientPOST.getClient().params.setParameter("http.connection.timeout", 30000)
        cgClientPOST.getClient().params.setParameter("http.socket.timeout", 30000)

        def pUsername;
        def pPassword;
        if (sourceUrl.contains('github')) {
            pUsername = 'aurea-github-service-account'
            pPassword = 'c2334575b128beefda874b1220179dc4ab5c3489'
        } else {
            pUsername = 'service.adfp.jenkins'
            pPassword = ')Cf$a8ct7QAWkD2+'
        }

        println sourceUrl + ", , ,"
//        cgClientPOST.request(Method.POST, ContentType.JSON) { req2 ->
//            body = [language    : 'java',
//                    repo_url    : sourceUrl,
//                    type        : 'git',
//                    username    : pUsername,
//                    password    : pPassword,
//                    author_name : 'alexandre.janoni',
//                    author_email: 'alexandre.janoni@aurea.com']
//
//            response.success = { resp2, json2 ->
//                println ">>>" + json2.db_id + "," + sourceUrl + "," + pUsername + "," + pPassword
//            }
//
//            response.failure = { resp2 ->
//                println "Unexpected error: ${resp2.statusLine.statusCode}"
//                println $ { resp2.statusLine.reasonPhrase }
//            }
//        }
    } else {
        if (ret.size() > 1) {
            ret = ret.sort {
                it.get('commitDate')
            }.reverse()
        }

        def cgProject = ret[0]

        //println sourceUrl + "," + cgProject["branch"] + "," + cgProject["revision"] + "," + cgProject["requestId"]
        verifyAndDelete(cgProject, sourceUrl)

    }


}

private void verifyAndDelete(cgProject, sourceUrl) {
    def cgClient = new RESTClient('https://codegraph-api-prod.ecs.devfactory.com/api/1.0/graphs/' + cgProject["requestId"] + '/query')
    cgClient.getClient().params.setParameter("http.connection.timeout", 30000)
    cgClient.getClient().params.setParameter("http.socket.timeout", 30000)
    boolean hasResults = false;
    def query = "MATCH (n)\n" +
            "WHERE NOT n.file IS NULL\n" +
            "AND n.file CONTAINS ('.java')\n" +
            "RETURN DISTINCT n.file LIMIT 1"
    try {
        cgClient.request(Method.POST, ContentType.JSON) { req ->
            body = [query: query, querytype: 'cypher', resulttype: 'row']
            response.success = { resp, json2 ->
                json2.results.data.row.each {
                    it.each {
                        hasResults = true
                    }
                }
            }

            response.failure = { resp ->
                println "Unexpected error: ${resp.statusLine.statusCode}"
                println $ { resp.statusLine.reasonPhrase }
            }
        }
    } catch (Exception ex ) {
        ex.printStackTrace();
    }

    if (hasResults) {
        println "OK," + sourceUrl + "," + cgProject["branch"] + "," + cgProject["revision"] + "," + cgProject["requestId"]
    } else {
        println "NOK," + sourceUrl + "," + cgProject["branch"] + "," + cgProject["revision"] + "," + cgProject["requestId"]

//        def cgClientDelete = new RESTClient('https://codegraph-api-prod.ecs.devfactory.com/api/1.0/graphs/' + cgProject["requestId"])
//        cgClientDelete.getClient().params.setParameter("http.connection.timeout", 30000)
//        cgClientDelete.getClient().params.setParameter("http.socket.timeout", 30000)
//
//        cgClientDelete.request(Method.DELETE) {
//            response.success = { resp, json3 ->
//                println cgProject["requestId"] + " DELETED."
//            }
//
//            response.failure = { resp ->
//                println "Unexpected error: ${resp.statusLine.statusCode}"
//                println $ { resp.statusLine.reasonPhrase }
//            }
//        }

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
