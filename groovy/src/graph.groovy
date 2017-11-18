import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
@Grab('com.opencsv:opencsv:3.9')
@Grab('com.sun.jersey:jersey-bundle:1.19.3')
@Grab('org.json:json:20170516')
@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7')
@Grab('mysql:mysql-connector-java:5.1.39')
@GrabConfig(systemClassLoader = true)

import groovy.sql.Sql
import groovy.transform.EqualsAndHashCode
import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.apache.commons.collections.CollectionUtils

import java.nio.charset.StandardCharsets

/**
 * Created by ajanoni on 18/11/17.
 *
 * ####################################################################
 * !!!!! IMPORTANT !!!!!
 * If you are running it from IntelliJ:
 * Alt+Enter with a caret positioned on @Grab to download artifacts.
 * ####################################################################
 *
 */

main();

class Repository {
    String sourceUrl
    String branch
    String revision
    String language
}

void main() {
    def cli = new CliBuilder(usage: 'graph.groovy -[force] file');
    cli.with
            {
                force(longOpt: 'force', 'Force trigger', required: false)
            }
    def opt = cli.parse(args)

    if (!opt) return

    if(opt.arguments().isEmpty()) {
        cli.usage()
        return
    }
    file = opt.arguments().get(0);

    try {
        List<Repository> listOfRepositories = loadFromFile(file);
        listOfRepositories.each {it ->
            postGraphRequest(it);
        }
    } catch(FileNotFoundException e) {
        println e.getMessage()
    }

}

List<Repository> loadFromFile(file) {
    String[] CSV_REPORT_MAPPING = ["sourceUrl", "branch", "revision", "language"]
    File fileCsv = new File(file)
    Reader fileCsvReader = new InputStreamReader(new FileInputStream(fileCsv), StandardCharsets.UTF_8)
    CSVReader csvReader = new CSVReader(fileCsvReader);
    ColumnPositionMappingStrategy<ReportInput> mappingStrategy =
            new ColumnPositionMappingStrategy<>();
    mappingStrategy.setType(Repository.class);
    mappingStrategy.setColumnMapping(CSV_REPORT_MAPPING);
    CsvToBean<ReportInput> ctb = new CsvToBean<>();
    return ctb.parse(mappingStrategy, csvReader);
}



void postGraphRequest(Repository repository) {
    def cgClientPOST = new RESTClient('http://brp-exp.ecs.devfactory.com')
    cgClientPOST.getClient().params.setParameter("http.connection.timeout", 30000)
    cgClientPOST.getClient().params.setParameter("http.socket.timeout", 30000)


    cgClientPOST.request(Method.POST, ContentType.JSON) { req ->
        body =  [   scm_url     : repository.sourceUrl,
                    branch      : repository.branch,
                    language    : repository.language,
                    revision    : repository.revision
                ]

        response.success = { resp, json ->
            println ">>> IMPORTING:" + json.requestId + "," + json.acccountId + "," + json.status
        }

        response.failure = { resp ->
            println "Unexpected error: ${resp.statusLine.statusCode}"
            println "${resp}"
        }
    }
}
