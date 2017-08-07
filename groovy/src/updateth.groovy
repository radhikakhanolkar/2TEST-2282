import groovy.sql.Sql
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

//cgProjects = [
//        'https://scm-ba.devfactory.com/scm/cap/business-payment.git'  : 'bef53d1aa446076ea0217d85ab89c99d99da7a3a',
//        'https://github.com/trilogy-group/aurea-aes-edi.git'          : 'af3dc86',
//        'https://github.com/trilogy-group/aurea-java-brp-cs-ruletest' : '',
//        'https://github.com/trilogy-group/aurea-lyris-platform-edge'  : '15e8699d82de854f5e4d6c40fe137056afdd9854',
//        'https://github.com/trilogy-group/aurea-sonic-mq'             : '2b8497b82efb8f2af5ba016a006d1349b224d9ea',
//        'https://github.com/trilogy-group/devfactory-codegraph-server': 'fc3450cfa4fd1cce236daf0e4dbaf891104bd8bb',
//        'https://github.com/trilogy-group/ignite-sensage-analyzer.git': 'f46a44ecbc5e95f778fb4f72cdb86509632b28d2',
//        'https://github.com/trilogy-group/kerio-mykerio-kmanager'     : 'e40fd363d019e8ff29a90cbc974818abdd4dae7c',
//        'https://github.com/trilogy-group/ta-smartleads-lms-mct.git'  : 'db45cdba9dce7cd97e721f954e1d7c1b3f0dbb6f',
//        'https://github.com/trilogy-group/versata-m1.ems.git'         : '1045056ed830b808cca82ff4d06b4d9add50064c',
//        'https://scm.devfactory.com/stash/scm/uppowersteering/pss.git': '18d5ec487055ce16a639bdd8efb7ab434ffba4bf',
//        'https://github.com/trilogy-group/wicket.git'                 : '0889d9ec569ff8a3edf17881941e52e9155bbb65',
//        'https://github.com/trilogy-group/drools.git'                 : '0a526a9876cc61374a50cf60a9c0bdf89e98ecd8',
//        'https://github.com/trilogy-group/spring-framework.git'       : '88d3526ef1fcf0dc2bc9098159d98eaf472c245f'
//]


cgProjects = [
        'https://github.com/trilogy-group/ignite-acorn-aaa'                         : '',
        'https://github.com/trilogy-group/ignite-acorn-pa5g'                        : '',
        'https://scm.devfactory.com/stash/scm/dfidep/dfideplugins'                  : '',
        'https://github.com/trilogy-group/aurea-nextdocs-nextdocs61x'               : '',
        'https://scm.devfactory.com/stash/scm/crossover/dotnettracker'              : '',
        'https://github.com/trilogy-group/gfi-mail-archiver'                        : '',
        'https://github.com/trilogy-group/gfi-directory-service'                    : '',
        'https://github.com/trilogy-group/gfi-endpointsecurity'                     : '',
        'https://github.com/trilogy-group/gfi-eventsmanager'                        : '',
        'https://github.com/trilogy-group/gfi-faxmaker-online'                      : '',
        'https://github.com/trilogy-group/gfi-faxmaker-server'                      : '',
        'https://github.com/trilogy-group/gfi-languard'                             : '',
        'https://github.com/trilogy-group/gfi-mail-essentials'                      : '',
        'https://github.com/trilogy-group/gfi-oneconnect-M1-EMSClient'              : '',
        'https://github.com/trilogy-group/gfi-oneguard'                             : '',
        'https://github.com/trilogy-group/gfi-web-monitor'                          : '',
        'https://scm.devfactory.com/stash/scm/aurea-dfi/dotnet-importer'            : '',
        'https://github.com/trilogy-group/versata-m1.client-ems-client'             : '',
        'https://scm.devfactory.com/stash/scm/upland-filebound/leadlander.git'      : '',
        'https://scm.devfactory.com/stash/scm/upland-filebound/filebound_v7'        : '',
        'https://scm.devfactory.com/stash/scm/upland/filebound_v7'                  : '',
        'https://scm.devfactory.com/stash/scm/upland-filebound/projectlviv'         : '',
        'https://scm.devfactory.com/stash/scm/upland-filebound/filebound_capture_v7': '',
        'https://scm.devfactory.com/stash/scm/UPLAND-ECLIPSE/eclipse-tfs-2-git'     : '',
        'https://github.com/trilogy-group/vepmlive-epmlive2013release'              : '',
        'https://github.com/johnsonz/MvcContosoUniversity'                          : '',
        'https://github.com/NancyFx/Nancy'                                          : '',
        'https://github.com/zeromq/netmq'                                           : '',
        'https://github.com/JamesNK/Newtonsoft.Json'                                : '',
        'https://github.com/nhibernate/nhibernate-core'                             : '',
        'https://github.com/nunit/nunit'                                            : '',
        'https://github.com/restsharp/RestSharp'                                    : '',
        'https://github.com/markrendle/Simple.Data'                                 : '',
        'https://github.com/danielpalme/MVCBlog'                                    : '',
        'https://github.com/JoshClose/CsvHelper'                                    : '',
        'https://github.com/Reactive-Extensions/Rx.NET'                             : '',
        'https://github.com/NSwag/NSwag'                                            : '',
        'https://github.com/jagregory/fluent-nhibernate'                            : '',
        'https://github.com/mongodb/mongo-csharp-driver'                            : '',
        'https://github.com/paulcbetts/refit'                                       : '',
        'https://github.com/paulcbetts/ModernHttpClient'                            : '',
        'https://github.com/ravendb/ravendb'                                        : '',
        'https://github.com/mbdavid/LiteDB'                                         : '',
        'https://github.com/aspnet/Microsoft.Data.Sqlite'                           : '',
        'https://github.com/aspnet/EntityFramework6'                                : '',
        'https://github.com/aspnet/Razor'                                           : '',
        'https://github.com/aspnet/dotnet-watch'                                    : '',
        'https://github.com/aspnet/MusicStore'                                      : '',
        'https://github.com/aspnet/NerdDinner'                                      : '',
        'https://github.com/NancyFx/Nancy.Blog'                                     : '',
        'https://github.com/NancyFx/Nancy.Demo.Samples'                             : '',
        'https://github.com/AvaloniaUI/Avalonia'                                    : '',
        'https://github.com/StackExchange/dapper-dot-net'                           : '',
        'https://github.com/AutoMapper/AutoMapper'                                  : '',
        'https://github.com/Redth/PushSharp'                                        : '',
        'https://github.com/PavelTorgashov/FastColoredTextBox'                      : '',
        'https://github.com/SignalR/SignalR'                                        : '',
        'https://github.com/opserver/Opserver'                                      : '',
        'https://github.com/reactiveui/ReactiveUI'                                  : '',
        'https://github.com/cefsharp/CefSharp'                                      : '',
        'https://github.com/NLog/NLog'                                              : '',
        'https://github.com/HangfireIO/Hangfire'                                    : '',
        'https://github.com/FransBouma/Massive'                                     : '',
        'https://github.com/JeremySkinner/FluentValidation'                         : '',
        'https://github.com/StackExchange/StackExchange.Redis'                      : '',
        'https://github.com/quartznet/quartznet'                                    : '',
        'https://github.com/Topshelf/Topshelf'                                      : '',
        'https://github.com/kevin-montrose/Jil'                                     : '',
        'https://github.com/schambers/fluentmigrator'                               : '',
        'https://github.com/akavache/Akavache'                                      : '',
        'https://github.com/PerfDotNet/BenchmarkDotNet'                             : '',
        'https://github.com/Antaris/RazorEngine'                                    : '',
        'https://github.com/ServiceStack/ServiceStack.Redis'                        : '',
        'https://github.com/aspnet/KestrelHttpServer'                               : '',
        'https://github.com/OrchardCMS/Orchard'                                     : '',
        'https://github.com/NEventStore/NEventStore'                                : '',
        'https://github.com/jstedfast/MailKit'                                      : '',
        'https://github.com/MediaBrowser/Emby'                                      : '',
        'https://github.com/mgravell/protobuf-net'                                  : '',
        'https://github.com/npgsql/npgsql'                                          : '',
        'https://github.com/CollaboratingPlatypus/PetaPoco'                         : '',
        'https://github.com/EasyNetQ/EasyNetQ'                                      : '',
        'https://github.com/ServiceStack/ServiceStack.Text'                         : '',
        'https://github.com/sjdirect/abot'                                          : '',
        'https://github.com/madskristensen/MiniBlog'                                : '',
        'https://github.com/nopSolutions/nopCommerce'                               : '',
        'https://github.com/oxyplot/oxyplot'                                        : '',
        'https://github.com/MarlabsInc/SocialGoal'                                  : '',
        'https://github.com/beto-rodriguez/Live-Charts'                             : '',
        'https://github.com/smartstoreag/SmartStoreNET'                             : '',
        'https://github.com/smsohan/MvcMailer'                                      : '',
        'https://github.com/StackExchange/NetGain'                                  : '',
        'https://github.com/fluentscheduler/FluentScheduler'                        : '',
        'https://github.com/DotNetAnalyzers/StyleCopAnalyzers'                      : '',
        'https://github.com/Knagis/CommonMark.NET'                                  : '',
        'https://github.com/pascalabcnet/pascalabcnet'                              : '',
        'https://github.com/PowerPointLabs/PowerPointLabs'                          : '',
        'https://github.com/naudio/NAudio'                                          : '',
        'https://github.com/apache/logging-log4net'                                 : ''
]

println ">>> SCRIPT STARTED <<<"

urlCG = 'http://codegraph-api-prod.ecs.devfactory.com/api/1.0/graphs?status=Completed&active=true'

println ">>> URL CODEGRAPH: " + urlCG
println ">>> GMT:" + new Date().toGMTString()

updateDatabase = false; //CHANGE HERE

showOffline = true
showOnline = true

def cgClient = new RESTClient(urlCG)
cgClient.getClient().params.setParameter("http.connection.timeout", 20000)
cgClient.getClient().params.setParameter("http.socket.timeout", 21000)

cgProjects.each { sourceUrl, revision ->
    retry(3, { e -> e.printStackTrace() }) {
        cgClient.request(Method.GET) {
            response.success = { resp, json ->
                processResponse(json, sourceUrl, revision)
                //processResponseGetOffline(json)
            }

            response.failure = { resp ->
                println "Unexpected error: ${resp.statusLine.statusCode}"
                println $ { resp.statusLine.reasonPhrase }
            }
        }
    }
}


private void processResponseGetOffline(json) {
    def ret = json.findAll {
        it.find { key, value ->
            key == 'deployStatus' &&
                    value != 'Online'
        }
    }

    ret.each { it -> println(it.get('requestId') + it.get('deployStatus')) }
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
        println ">>> " + sourceUrl + " : " + revision + " : " + " - NOT FOUND IN CODEGRAPH."
        return
    }

    if (ret.size() > 1) {
        ret = ret.sort {
            it.get('commitDate')
        }.reverse()
    }

    def cgProject = ret[0]

    cdgrphx_pdg_task:9176
    try {
        if (!cgProject['neo4jUrl']?.trim()) {
            if(showOffline) println ">>> " + sourceUrl + " : " + revision + " : NO PORT ASSIGNED - OFFLINE. requestId: " + cgProject['requestId']
            return
        }

        def neo4jClient = new RESTClient(cgProject['neo4jUrl'])
        neo4jClient.getClient().params.setParameter("http.connection.timeout", 20000)
        neo4jClient.getClient().params.setParameter("http.socket.timeout", 21000)

        neo4jClient.request(Method.GET) {
            response.success = { respNeo, jsonNeo ->
                if(showOnline) println sourceUrl + "," + cgProject['requestId'] + "," + cgProject['revision']
                    //println ">>> " + sourceUrl + " : " + revision + " : " + cgProject['neo4jUrl'] + " - ONLINE. requestId: " + cgProject['requestId']

                if (updateDatabase) {
                    updateDB(sourceUrl, revision, cgProject)
                }
            }

            response.failure = { respNeo ->
                if(showOffline) println ">>> " + sourceUrl + " : " + revision + " : " + cgProject['neo4jUrl'] + " - OFFLINE. requestId: " + cgProject['requestId'] + " serviceName: " + cgProject['serviceName']
            }
        }
    } catch (Exception ex) {
        if(showOffline) println ">>> " + sourceUrl + " : " + revision + " : " + cgProject['neo4jUrl'] + " - OFFLINE. requestId: " + cgProject['requestId'] + " serviceName: " + cgProject['serviceName']
    }
}


private updateDB(String sourceUrl, String revision, Map cgProject) {

    def dbUrl = 'jdbc:mysql://devfactory-aurora-1.cluster-cd1ianm7fpxp.us-east-1.rds.amazonaws.com';
    def dbPort = 3306
    def dbSchema = 'javabrp_harness_tst'
    def dbUser = 'javabrp_harness'
    def dbPassword = '9&4i0@&k98Wp'
    def dbDriver = 'com.mysql.jdbc.Driver'

    Sql.withInstance(dbUrl + ':' + dbPort + '/' + dbSchema, dbUser, dbPassword, dbDriver) { sql ->

        def verifyIds = "SELECT count(1) AS num, " +
                "cb.id AS cbId, " +
                "neodb.id AS neodbId " +
                "FROM neo4jdatabases neodb " +
                "INNER JOIN codebases cb ON (neodb.Codebase_id = cb.id) " +
                "WHERE cb.RepoUrl LIKE CONCAT(:REPO_URL, '%') "

        if (revision?.trim()) {
            verifyIds += "AND cb.revision = :REVISION "
        } else {
            verifyIds += "AND cb.revision IS NULL "
        }

        def first = sql.firstRow verifyIds, REPO_URL: sourceUrl - '.git', REVISION: revision

        if (first.num == 1) {
            def updateCodebases = 'UPDATE codebases ' +
                    'SET CodeGraphDbId = :CODEGRAPH_DB_ID ' +
                    'WHERE id = :CB_ID'

            def countCodebases = sql.executeUpdate updateCodebases,
                    CODEGRAPH_DB_ID: cgProject['requestId'],
                    CB_ID: first.cbId

            def updateNeo4jdatabases = 'UPDATE neo4jdatabases ' +
                    ' SET CodeGraphDbId = :CODEGRAPH_DB_ID ,' +
                    ' BoltPort = :BOLT_PORT,' +
                    ' HttpPort = :HTTP_PORT,' +
                    ' Host = :HOST,' +
                    ' DatabaseUrl = :DB_URL,' +
                    ' CodeGraphDbId = :CODEGRAPH_DB_ID' +
                    ' WHERE id = :NEODB_ID;'

            def countNeo4jdatabases = sql.executeUpdate updateNeo4jdatabases,
                    CODEGRAPH_DB_ID: cgProject['requestId'],
                    BOLT_PORT: cgProject['bolt'],
                    HTTP_PORT: cgProject['http'],
                    HOST: cgProject['host'],
                    DB_URL: cgProject['neo4jUrl'],
                    NEODB_ID: first.neodbId

            if (countCodebases == 1 && countNeo4jdatabases == 1) {
                println ">>> Test harness DB updated successfully."
            } else {
                println ">>> Error while updating test harness db."
            }

        } else if (first.num == 0) {
            println "No records found."
        } else {
            println "Invalid number of records found."
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
