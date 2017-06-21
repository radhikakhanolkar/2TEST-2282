import groovy.sql.Sql
@Grab('com.opencsv:opencsv:3.9')
@Grab('com.sun.jersey:jersey-bundle:1.19.3')
@Grab('org.json:json:20170516')
@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7')
@Grab('mysql:mysql-connector-java:5.1.39')
@GrabConfig(systemClassLoader = true)

import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.apache.http.conn.ConnectTimeoutException

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
        'https://scm-ba.devfactory.com/scm/cap/business-payment.git'  : 'bef53d1aa446076ea0217d85ab89c99d99da7a3a',
        'https://github.com/trilogy-group/aurea-aes-edi.git'          : 'af3dc86',
        'https://github.com/trilogy-group/aurea-java-brp-cs-ruletest' : '',
        'https://github.com/trilogy-group/aurea-lyris-platform-edge'  : '15e8699d82de854f5e4d6c40fe137056afdd9854',
        'https://github.com/trilogy-group/aurea-sonic-mq'             : '2b8497b82efb8f2af5ba016a006d1349b224d9ea',
        'https://github.com/trilogy-group/devfactory-codegraph-server': 'fc3450cfa4fd1cce236daf0e4dbaf891104bd8bb',
        'https://github.com/trilogy-group/ignite-sensage-analyzer.git': 'f46a44ecbc5e95f778fb4f72cdb86509632b28d2',
        'https://github.com/trilogy-group/kerio-mykerio-kmanager'     : 'e40fd363d019e8ff29a90cbc974818abdd4dae7c',
        'https://github.com/trilogy-group/ta-smartleads-lms-mct.git'  : 'db45cdba9dce7cd97e721f954e1d7c1b3f0dbb6f',
        'https://github.com/trilogy-group/versata-m1.ems.git'         : '1045056ed830b808cca82ff4d06b4d9add50064c',
        'https://scm.devfactory.com/stash/scm/uppowersteering/pss.git': '18d5ec487055ce16a639bdd8efb7ab434ffba4bf'
]


println ">>> SCRIPT STARTED <<<"

urlCG = 'http://codegraph-api-prod.ecs.devfactory.com/api/1.0/graphs?status=Completed&active=true'

println ">>> URL CODEGRAPH: " + urlCG
println ">>> GMT:" + new Date().toGMTString()

updateDatabase = false; //CHANGE HERE

def cgClient = new RESTClient(urlCG)
cgClient.getClient().params.setParameter("http.connection.timeout", 10000)
cgClient.getClient().params.setParameter("http.socket.timeout", 11000)

cgProjects.each { sourceUrl, revision ->
    retry(3, { e -> e.printStackTrace() }) {
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
        println ">>> " + sourceUrl + " : " + revision + " : " + " - NOT FOUND IN CODEGRAPH."
        return
    }

    if (ret.size() > 1) {
        ret = ret.sort {
            it.get('commitDate')
        }.reverse()
    }

    def cgProject = ret[0]


    try {
        if (!cgProject['neo4jUrl']?.trim()) {
            println ">>> " + sourceUrl + " : " + revision + " : NO PORT ASSIGNED - OFFLINE. requestId: " + cgProject['requestId']
            return
        }

        def neo4jClient = new RESTClient(cgProject['neo4jUrl'])
        neo4jClient.getClient().params.setParameter("http.connection.timeout", 10000)
        neo4jClient.getClient().params.setParameter("http.socket.timeout", 11000)
        neo4jClient.request(Method.GET) {
            response.success = { respNeo, jsonNeo ->
                println ">>> " + sourceUrl + " : " + revision + " : " + cgProject['neo4jUrl'] + " - ONLINE. requestId: " + cgProject['requestId']

                if (updateDatabase) {
                    updateDB(sourceUrl, revision, cgProject)
                }
            }

            response.failure = { respNeo ->
                println ">>> " + sourceUrl + " : " + revision + " : " + cgProject['neo4jUrl'] + " - OFFLINE. requestId: " + cgProject['requestId']
            }
        }
    } catch (Exception ex) {
        println ">>> " + sourceUrl + " : " + revision + " : " + cgProject['neo4jUrl'] + " - OFFLINE. requestId: " + cgProject['requestId']
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
