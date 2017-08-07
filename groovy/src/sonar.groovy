/**
 * Created by ajanoni on 31/05/17.
 *
 *
 * ####################################################################
 * !!!!! IMPORTANT !!!!!
 * If you are running it from IntelliJ:
 * Alt+Enter with a caret positioned on @Grab to download artifacts.
 * ####################################################################
 *
 */


@Grab('com.opencsv:opencsv:3.9')
@Grab('com.sun.jersey:jersey-bundle:1.19.3')
@Grab('org.json:json:20170516')
@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7')

import com.opencsv.CSVWriter
import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource
import com.sun.jersey.api.client.config.ClientConfig
import com.sun.jersey.api.client.config.DefaultClientConfig
import com.sun.jersey.api.json.JSONConfiguration
import groovy.transform.EqualsAndHashCode
import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.nio.charset.StandardCharsets

@EqualsAndHashCode(excludes = ['col'])
class Violation {
    String projectName
    String file
    String fileName
    Integer line
    Integer col
}

String.metaClass.encodeURL = {
    java.net.URLEncoder.encode(delegate, "UTF-8")
}

cgErrorList = new ArrayList<String[]>()

//queryS2444 = "MATCH (nField:FieldDeclaration)-[:tree_edge]->(nVar:VariableDeclarationFragment)<-[:SETS]-(nMethod:MethodDeclaration) MATCH (nMethod)-[:tree_edge*]->(var)<-[:SET_BY]-(nVar) WHERE nField.modifiers CONTAINS 'static' AND NOT nField.modifiers CONTAINS 'volatile' AND NOT nField.modifiers CONTAINS 'final' AND NOT nMethod.modifiers CONTAINS 'synchronized' AND NOT (var)<-[:tree_edge*]-(:SynchronizedStatement) AND NOT (nField)-[:tree_edge*]->(:PrimitiveType) RETURN DISTINCT var.col AS col, var.line AS line, var.file AS file ORDER BY var.file, var.line";

// queryS1143 = "MATCH (n:ReturnStatement)<-[:tree_edge*]-(:Block)<-[:finally]-(:TryStatement) " +
//        "RETURN DISTINCT n.col AS col, n.line AS line, n.file AS file " +
//        "UNION " +
//        "MATCH (n:BreakStatement)<-[:tree_edge*]-(:Block)<-[:finally]-(:TryStatement) " +
//        "RETURN DISTINCT n.col AS col, n.line AS line, n.file AS file " +
//        "UNION " +
//        "MATCH (n:ContinueStatement)<-[:tree_edge*]-(:Block)<-[:finally]-(:TryStatement) " +
//        "RETURN DISTINCT n.col AS col, n.line AS line, n.file AS file " +
//        "UNION " +
//        "MATCH (n:ThrowStatement)<-[:tree_edge*]-(:Block)<-[:finally]-(:TryStatement) " +
//        "RETURN DISTINCT n.col AS col, n.line AS line, n.file AS file"

//queryS1444 = "MATCH (c)-[:tree_edge*]->(field:FieldDeclaration) " +
//        "WHERE field.modifiers CONTAINS ('static') " +
//        "AND field.modifiers CONTAINS ('public') " +
//        "AND NOT field.modifiers CONTAINS ('final') " +
//        "AND NOT c.entity_type = 'interface' " +
//        "WITH COLLECT(DISTINCT(field)) AS All " +
//        "OPTIONAL MATCH (field)<-[:member]-(:TypeDeclaration)-[:annotation]->(sma:SingleMemberAnnotation) " +
//        "WHERE sma.name = 'StaticMetamodel' " +
//        "WITH COLLECT(DISTINCT(field)) AS SmaClasses, All " +
//        "WITH FILTER(x IN All WHERE NOT x IN SmaClasses) AS FilteredResult " +
//        "UNWIND FilteredResult AS FinalResult " +
//        "MATCH(FinalResult)-[:fragment]->(vd:VariableDeclarationFragment) " +
//        "RETURN vd.col AS col, vd.line AS line, vd.file AS file"


queryDM_NUMBER_CTOR = "MATCH (newInstance:ClassInstanceCreation)-[:type]->(type:SimpleType)\n" +
        "WHERE type.name in ['Long','Integer', 'Short', 'Character', 'Byte']\n" +
        "AND NOT newInstance.file CONTAINS ('src/test') //Do not get violations in test files\n" +
        "RETURN DISTINCT newInstance.col as col, newInstance.line as line, newInstance.file as file\n" +
        "ORDER BY newInstance.file,newInstance.line\n"


queryS1116 = "MATCH (n:EmptyStatement)\n" +
        "RETURN DISTINCT n.col as col, n.line as line, n.file as file"

//cgProjects = [
//        'business-payment'                        : 'a98c9c54-23c1-4dfc-a9d1-5335c1368af3',
//        'versata-m1.ems'                          : 'e494eb5f-5a45-4259-b2a7-714f16dbd6b1',
//        'aurea-sonic-mq'                          : '5beea8ba-c579-46f8-8c9e-c94124a4f12e',
//        'ignite-sensage-analyzer'                 : '28ade68a-dd2e-405e-a87a-549ecc0cf57d',
//        'ta-smartleads-lms-mct'                   : '223915ff-d7cc-4acc-9b23-6c238c77f39a',
//        'aurea-aes-edi'                           : '7fc8e251-9fca-4861-b245-8ccde4580f67',
//        'pss'                                     : 'aad7ba6b-8069-4aa3-8a36-38cc5c5e20d1',
//        'kerio-mykerio-kmanager'                  : 'cb7cd6df-a193-444f-8a17-633da0025a18',
//        'aurea-lyris-platform-edge'               : '34267f92-0883-4004-bd33-1c570ab54552',
//        'devfactory-codegraph-server'             : '0db9b092-2e84-438e-949e-5abaad903dad',
//        'aurea-java-brp-cs-ruletest'              : '9a3f2b9b-3bfa-4b99-b363-f1d0659fa778',
//        'org.jenkins-ci.main:pom'                 : '15e366aa-fc18-4ea0-bec5-9443a2a7a8f4',
//        'hibernate-orm'                           : 'f65e663c-ab46-44e2-be13-03d84eda1bf3',
//        'org.apache.wicket:wicket-parent'         : '37821c2d-f736-46bc-b1a4-fa2400dab0e3',
//        'org.apache.struts:struts2-parent'        : 'f31fed00-936f-47ae-8f83-71e6e223b8a8',
//        'org.springframework:spring'              : 'ce1ab499-2bc4-49c8-adf9-8bf1f4e2e1e7',
//        'org.apache.hive:hive'                    : '68151e65-78ab-46f8-aa28-7e90ef6168f8',
//        'org.drools:drools'                       : 'b6546cab-7dd2-4748-99af-738658c7b5d7',
//        'org.apache.activemq:activemq-parent'     : 'ea4b9d5a-35d3-4810-b605-7f7983062ff9',
//        'org.eclipse.jetty:jetty-project'         : '1def6b67-e748-43e4-90a8-d9627e6688c6',
//        'org.eclipse.jgit:org.eclipse.jgit-parent': '43c578b3-bd45-4fee-8ba4-1acfdb449ab8'
//]



cgProjects = [
        'ignite-acorn-aaa':'3c4c53bc-10d3-49fb-a10e-d40a0be96b71',
        'ignite-acorn-pa5g':'c469b40d-437e-4993-8832-722bf28b9051',
        'aurea-nextdocs-nextdocs61x':'a0715165-e0db-49b3-8d2a-a55cc03e7d80',
        'dotnettracker':'8683f7e3-c161-4331-986f-980f0673c40f',
        'gfi-mail-archiver':'ccff9ea9-085f-4d17-840e-3e92b635803d',
        'gfi-directory-service':'dca7bc04-c280-4f3a-b06e-100ced06d40e',
        'gfi-endpointsecurity':'29be77c9-cd91-428b-a562-2f7ccd04f90f',
        'gfi-faxmaker-online':'3042f932-6ce2-4f95-84ac-351c31ddde66',
        'gfi-languard':'3d7f1042-5136-4186-93a1-81f85b630cba',
        'gfi-mail-essentials':'7784c65e-9cb3-4d30-afd3-816400fbf4a4',
        'gfi-oneconnect-M1-EMSClient':'4abca461-1195-4dc2-b98a-84cedacdca23',
        'gfi-oneguard':'1a3a9cb4-c2d0-4e46-85dd-b29bc32112c5',
        'gfi-web-monitor':'561f1ad1-ca8e-49a9-8dd1-4c270bc8412e',
        'dotnet-importer':'cdd0f135-f158-434b-9ea2-0d290a4044c9',
        'versata-m1.client-ems-client':'0ee87265-a7a7-4723-8f86-7068fea5ff6b',
        'leadlander':'f476cbdd-ba5a-45e0-bbba-d72fe55e30c5',
        'filebound_v7_dev':'0fc5447a-0871-46d4-9f16-6522ae893bde',
        'filebound_v7_qa':'50b1b37d-3e3f-44b0-be9a-fa31f4003a2e',
        'projectlviv':'0ffab9a2-a572-413b-a756-093a50072871',
        'filebound_capture_v7':'0b0da000-ddbd-465f-bd64-ade9bfdf5970',
        'eclipse-tfs-2-git':'bbf3ec77-dbb7-4894-b84a-064018952a76',
        'vepmlive-epmlive2013release':'97b7e16e-6725-4005-b381-a9114e672d45',
        'MvcContosoUniversity':'adbfd587-3e57-476c-9bfb-608814054fd7',
        'Nancy':'69c843ee-2801-469b-99c8-fa7cc1ad2406',
        'netmq':'074bd65f-b176-488a-9c4d-e871e0db6681',
        'Newtonsoft.Json':'d18b5701-90ca-49cf-b3d3-64e4b1af75fe',
        'nhibernate-core':'196fddf1-fe19-4310-be5a-1936147c6aa9',
        'nunit':'cba76385-80c9-4b81-ba67-d247f0c4092d',
        'RestSharp':'fbf4cc37-3ad5-4412-a896-3dc9829b7df8',
        'Simple.Data':'ac98319b-c897-4ab6-a909-be933334a804',
        'MVCBlog':'c37386b7-9b0e-4591-a7d1-fb12ae9e3997',
        'CsvHelper':'4ce67a69-8145-40e3-bd15-bb30eb80de78',
        'Rx.NET':'e9727372-138b-4bea-8570-5b50012a42c6',
        'NSwag':'0b7a6707-56e9-43a8-94bc-d090745d0c12',
        'fluent-nhibernate':'bcfbfa29-4e78-497b-a48a-7184bef10b8b',
        'mongo-csharp-driver':'dcc6defe-290d-427e-9154-56349744cb7c',
        'refit':'4291bfcc-a687-4063-afc0-6ca7f29bb7c6',
        'ModernHttpClient':'71633292-67b7-46d6-9d3a-10fab17d34c0',
        'ravendb':'c93b957c-255d-4c68-a97e-65d9e88e1597',
        'LiteDB':'a6364663-e24b-4526-ab5e-237942214475',
        'Microsoft.Data.Sqlite':'9f0f2a3f-8a87-40ba-87f3-b9f4c0fdcae3',
        'EntityFramework6':'d48ba663-841c-4235-8da4-c256d789c450',
        'Razor':'d85f0a2f-8ee0-4088-af4f-43594c8e4478',
        'dotnet-watch':'361aac07-a831-43b6-ad36-6e8fb56e60c1',
        'MusicStore':'5e55bdcb-e93a-452a-b95d-7bfdba6080b1',
        'NerdDinner':'8291c2b1-df82-42c1-90d0-c184592bf78f',
        'Nancy.Blog':'fa0bbd4d-3abe-404d-9eb7-bb42f5e1605c',
        'Nancy.Demo.Samples':'e9196e5c-07b5-4536-b67e-d39bd14ad393',
        'Avalonia':'b1e3fde7-f7fc-4f9d-921e-a141a0a88fba',
        'dapper-dot-net':'bb26ef47-217f-4dd0-909c-6cf427dc2abc',
        'AutoMapper':'c248945c-f95c-4907-964f-2b4ca93995ac',
        'PushSharp':'299f63b6-1a9e-4239-acda-9c67a3637a40',
        'FastColoredTextBox':'0ced836e-7ef3-4c24-baed-83a83413f5f5',
        'SignalR':'af142301-7fa6-462f-a9a7-c146aafe6521',
        'Opserver':'287b4e2a-e79a-4875-b5f3-e790e9632b49',
        'ReactiveUI':'14ab776a-53a1-400e-9787-9e50003383b5',
        'CefSharp':'7bd89d33-ff9d-4962-a5f3-5faeb5e2522a',
        'NLog':'36cfba3a-2b54-4358-aca4-39502abc381c',
        'Hangfire':'999b3eb7-1dde-4698-86d1-ad47039f64c7',
        'Massive':'a95ae622-187f-4842-8123-101dca67d23f',
        'FluentValidation':'7633a2d5-ff9f-4fa8-8278-df8371e8a648',
        'StackExchange.Redis':'0e99e584-c1c9-4e1e-93aa-512c7ffce259',
        'quartznet':'a7e66f6b-477c-42bd-a1a1-9067664cb673',
        'Topshelf':'c83e54d2-d368-40c1-b409-86424588beaa',
        'Jil':'f6a2e4a8-6d46-49de-b2a3-f368acb85c96',
        'fluentmigrator':'826efab7-dc6f-4311-982b-244bdbeb6936',
        'Akavache':'6dfb4ba5-43b3-4644-8f93-711f29570025',
        'BenchmarkDotNet':'109bce79-6bbe-4815-8acf-30799cdc8ede',
        'RazorEngine':'745e801a-0dec-4a62-9140-7161f8b8a4cf',
        'ServiceStack.Redis':'0ad20cbd-1b93-44b4-ba39-9d7067beee6d',
        'KestrelHttpServer':'1020141d-9e08-4cca-8412-a831c56fdf83',
        'Orchard':'fb3cf62e-e200-43b2-9839-c1ef27e355b5',
        'NEventStore':'15e480e6-f165-4e88-98de-b9f37bb47691',
        'MailKit':'9a3a899e-20ce-47cf-822b-d1a4432ec6c1',
        'Emby':'56c1f882-8743-4485-8010-45a583262561',
        'protobuf-net':'1df14255-4678-400a-8860-117bbe732dcb',
        'npgsql':'86b28c26-28fa-4ce0-ad87-7b94f2cc43e4',
        'PetaPoco':'aff8e90f-7113-44aa-989f-62ce0c38a244',
        'EasyNetQ':'c7b4dcd5-56cb-40f0-9fe2-8b106d2a56ba',
        'ServiceStack.Text':'5508a0ab-ac43-46f0-9353-313bae126b2a',
        'abot':'f18b72e2-bc18-460c-8cd3-929273414fa3',
        'MiniBlog':'92bb1556-89a0-4398-95ec-d6b129790a05',
        'nopCommerce':'e74f4e40-5c5d-4913-9a09-8ac5302088bc',
        'oxyplot':'b045cb40-d85e-4f76-b7b9-e3f57822b6d1',
        'SocialGoal':'56f26d1f-1261-4c22-b2ea-0ac5152b9a7e',
        'Live-Charts':'48378976-d14d-4806-9e5f-6781272ce5ec',
        'SmartStoreNET':'0109f83b-e203-4af7-9fb6-75d53061caf1',
        'MvcMailer':'518e58dd-5d95-4fe8-9dff-8d5951cf2d28',
        'NetGain':'e6b8b461-b91d-4b95-b0c6-8991f67fb06f',
        'FluentScheduler':'b5533dc7-3a1d-424d-aef6-6e69985b908c',
        'StyleCopAnalyzers':'898d4569-d1e2-45f8-8c90-60c46bff3ad0',
        'CommonMark.NET':'2ba2aba6-fb61-480b-8052-62343660dd68',
        'pascalabcnet':'fbf96395-3e10-42ea-a0e3-62b1f3d58581',
        'PowerPointLabs':'b8a9d954-aae6-4e9d-bb91-614402ae9329',
        'NAudio':'488936c7-4271-444a-b893-f71b5f9c6aaf',
        'logging-log4net':'d22b7ff2-5e77-4211-8ae6-5944cbf6ac0d'
]



fileLocal = "/Users/ajanoni/sonarcsv"

//sonarBaseUrl = "http://brp-sonar.ecs.devfactory.com"

//ruleId = "rules=squid%3AS1210" //CHANGE THE RULE NAME HERE
//ruleId = "rules=findbugs%3ADM_NUMBER_CTOR"

//sonarUrl = sonarBaseUrl + "/api/issues/search?" + componentRoots(cgProjects) + "&" + ruleId

sonarBaseUrl = "http://netbrp-sonarqube-stg.ecs.devfactory.com/"
ruleId = "rules=csharpsquid%3AS1116"
sonarUrl = sonarBaseUrl + "/api/issues/search?" + componentRoots(cgProjects) + "&" + ruleId
ruleName = "S1116"

sonarViolation = toViolationDTO(findViolations())

cgViolation = getViolationFromCG(queryS1116) //CHANGE THE QUERY HERE

exportToCsvSummary(ruleName, sonarViolation, cgViolation) //CHANGE THE QUERY HERE

saveCGErrors(ruleName)

//generateCsvPerRule()

Set<Violation> getViolationFromCG(String query) {

    Set<Violation> retViolation = new HashSet<>()

    cgProjects.each { k, v ->
        def cgClient = new RESTClient('https://codegraph-api-prod.ecs.devfactory.com/api/1.0/graphs/' + v + '/query')
        cgClient.getClient().params.setParameter("http.connection.timeout", 30000)
        cgClient.getClient().params.setParameter("http.socket.timeout", 30000)
        println k + ' - https://codegraph-api-prod.ecs.devfactory.com/api/1.0/graphs/' + v + '/query'
        retry(1, { e -> cgErrorList.add((String[]) [k, v])}) {
            cgClient.request(Method.POST, ContentType.JSON) { req ->
                body = [query: query, querytype: 'cypher', resulttype: 'row']
                response.success = { resp, json ->
                    json.results.data.row.each {
                        it.each {
                            String completeFile = it[2];
                            String fileName = completeFile.substring(completeFile.lastIndexOf("/"), completeFile.size())
                            retViolation.add(new Violation([projectName: k, file: completeFile, fileName: fileName, line: it[1], col: it[0]]))
                        }
                    }
                }

                response.failure = { resp ->
                    println "Unexpected error: ${resp.statusLine.statusCode}"
                    println $ { resp.statusLine.reasonPhrase }
                }
            }
        }
    }
    return retViolation
}


void generateCsvPerRule() {

    violations = findViolations()
    violations.each {
        project, rule ->
            rule.each {
                name, violation -> println project + " : Total violations: ${violation.size()}"
            }

    }
    exportToCsvDiff(violations)

}



void exportToCsvPerRule(Map<String, Map<String, List<String[]>>> projectRuleViolations) {

    File rbf = new File(fileLocal);
    boolean diretoryCreated = rbf.mkdirs();
    if (diretoryCreated) {
        println(String.format("Directory %s has been created.", rbf))
    }
    for (Map.Entry<String, Map<String, List<String[]>>> entryProject : projectRuleViolations.entrySet()) {
        String projectName = entryProject.getKey();
        for (Map.Entry<String, List<String[]>> entryRuleViolation :
                entryProject.getValue().entrySet()) {
            String ruleName = entryRuleViolation.getKey();

            resultPath = rbf.toString() + "/" + projectName + "_" + ruleName + ".csv"
            Writer fileRbfWriter = new OutputStreamWriter(
                    new FileOutputStream(resultPath),
                    StandardCharsets.UTF_8)
            CSVWriter reportWriter = new CSVWriter(fileRbfWriter, (char) ",")

            List<String[]> violations = entryRuleViolation.getValue();
            println projectName + "," + violations.size()
            for (String[] item : violations) {
                String[] reportLine = new String[3];
                reportLine[0] = item[3]; // col
                reportLine[1] = item[1]; // line
                reportLine[2] = item[0]; //file
                reportWriter.writeNext(reportLine);
            }

            reportWriter.flush();
            reportWriter.close();
        }

    }
}

void exportToCsvSummary(String ruleName, Set<Violation> sonarViolation, Set<Violation> cgViolation) {
    def projectCountCG = cgViolation.countBy { it.projectName }
    def projectCountSonar = sonarViolation.countBy { it.projectName }

    println ">>> FINAL RESULTS: \n"


    List<String[]> resultCsv = new ArrayList<>()

    cgProjects.each { k, v ->
        countCG = ""
        if(cgErrorList.any{it == [k,v]}){
            countCG = "Error"
        }else{
            countCG = (projectCountCG.get(k) ?: "0")
        }
        println k + "," + (projectCountSonar.get(k) ?: "0") + "," + countCG
        String[] reportLine = new String[3];
        reportLine[0] = k
        reportLine[1] = (projectCountSonar.get(k) ?: "0")
        reportLine[2] = countCG
        resultCsv.add(reportLine)
    }

    File rbf = new File(fileLocal);
    boolean diretoryCreated = rbf.mkdirs();
    if (diretoryCreated) {
        println(String.format("Directory %s has been created.", rbf))
    }

    resultPath = rbf.toString() + "/SUMMARY_" + ruleName + ".csv"
    Writer fileRbfWriter = new OutputStreamWriter(
            new FileOutputStream(resultPath),
            StandardCharsets.UTF_8)
    CSVWriter reportWriter = new CSVWriter(fileRbfWriter, (char) ",")

    reportWriter.writeNext((String[]) ['projectName', 'sonarCount', 'cgCount'])
    reportWriter.writeAll(resultCsv)
    reportWriter.flush();
    reportWriter.close();

}

void saveCGErrors(String ruleName){
    File rbf = new File(fileLocal);
    boolean diretoryCreated = rbf.mkdirs();
    if (diretoryCreated) {
        println(String.format("Directory %s has been created.", rbf))
    }

    resultPath = rbf.toString() + "/ERROR_" + ruleName + ".csv"
    Writer fileRbfWriter = new OutputStreamWriter(
            new FileOutputStream(resultPath),
            StandardCharsets.UTF_8)
    CSVWriter reportWriter = new CSVWriter(fileRbfWriter, (char) ",")

    reportWriter.writeAll(cgErrorList)
    reportWriter.flush();
    reportWriter.close();
}

void exportToCsvDiff(String ruleName, Set<Violation> sonarViolation, Set<Violation> cgViolation) {
    if (sonarViolation.isEmpty() && cgViolation.isEmpty()) {
        return;
    }

    def intersectViolation = sonarViolation.intersect(cgViolation)
    def onlyCG = cgViolation - sonarViolation
    def onlySonar = sonarViolation - cgViolation


    File rbf = new File(fileLocal);
    boolean diretoryCreated = rbf.mkdirs();
    if (diretoryCreated) {
        println(String.format("Directory %s has been created.", rbf))
    }

    resultPath = rbf.toString() + "/ALL_" + ruleName + ".csv"
    Writer fileRbfWriter = new OutputStreamWriter(
            new FileOutputStream(resultPath),
            StandardCharsets.UTF_8)
    CSVWriter reportWriter = new CSVWriter(fileRbfWriter, (char) ",")


    List<String[]> resultCsv = new ArrayList<>()
    intersectViolation.each {
        String[] reportLine = new String[7];
        reportLine[0] = it.projectName
        reportLine[1] = it.col
        reportLine[2] = it.line
        reportLine[3] = it.file
        reportLine[4] = it.col
        reportLine[5] = it.line
        reportLine[6] = it.file
        resultCsv.add(reportLine)
    }

    onlySonar.each {
        String[] reportLine = new String[7];
        reportLine[0] = it.projectName
        reportLine[1] = it.col
        reportLine[2] = it.line
        reportLine[3] = it.file
        reportLine[4] = '0'
        reportLine[5] = '0'
        reportLine[6] = it.file
        resultCsv.add(reportLine)
    }

    onlyCG.each {
        String[] reportLine = new String[7];
        reportLine[0] = it.projectName
        reportLine[1] = '0'
        reportLine[2] = '0'
        reportLine[3] = it.file
        reportLine[4] = it.col
        reportLine[5] = it.line
        reportLine[6] = it.file
        resultCsv.add(reportLine)
    }

    resultCsv.sort { a, b -> a[0] <=> b[0] ?: a[6] <=> b[6] ?: a[3] <=> b[3] ?: a[5] <=> b[5] ?: a[2] <=> b[2] ?: a[4] <=> b[4] ?: a[1] <=> b[1] }
    reportWriter.writeNext((String[]) ['projectName', 'sonarCol', 'sonarLine', 'sonarFile', 'cgCol', 'cgLine', 'cgFile'])
    reportWriter.writeAll(resultCsv)
    reportWriter.flush();
    reportWriter.close();

    println "===== Files not found in Sonar ====="
    println cgViolation.findAll {
        !sonarViolation.fileName.contains(it.fileName)
    }.file

    println "===== Files not found in CG ====="
    println sonarViolation.findAll {
        !cgViolation.fileName.contains(it.fileName)
    }.file

    def countCg = cgViolation.countBy { it.fileName }
    def countSonar = sonarViolation.countBy { it.fileName }

    countCg.intersect(countSonar).each {
        countCg.remove(it.key);
        countSonar.remove(it.key)
    }

    println "COUNTCG"
    println countCg

    println "COUNTSONAR"
    println countSonar



}


Set<Violation> toViolationDTO(Map<String, Map<String, List<String[]>>> projectRuleViolations) {
    Set<Violation> retViolationDTO = new HashSet<>()
    for (Map.Entry<String, Map<String, List<String[]>>> entryProject : projectRuleViolations.entrySet()) {
        String projectName = entryProject.getKey();
        for (Map.Entry<String, List<String[]>> entryRuleViolation :
                entryProject.getValue().entrySet()) {
            String ruleName = entryRuleViolation.getKey();

            List<String[]> violations = entryRuleViolation.getValue();
            for (String[] item : violations) {
                String completeFile = item[0];
                String fileName2 = completeFile;
                if (completeFile.contains("/")) {
                    fileName2 = completeFile.substring(completeFile.lastIndexOf("/"), completeFile.size())
                }
                retViolationDTO.add(new Violation([projectName: projectName, file: completeFile, fileName: fileName2, line: item[1].toInteger(), col: item[3].toInteger()]))
            }
        }
    }
    return retViolationDTO
}

// projectName, <rule , violations <file, startLine, endLine, startOffset, endOffset, message, type>>
Map<String, Map<String, List<String[]>>> findViolations(String project) throws JSONException {

    ClientConfig cc = new DefaultClientConfig();
    cc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    Client client = Client.create(cc);

    println sonarUrl

    WebResource webResource = client.resource(sonarUrl)
            .queryParam("ps", String.valueOf(100))
            .queryParam("p", "1")
            .queryParam("statuses", "OPEN,REOPENED");
    ClientResponse response =
            webResource.accept("application/json").type("application/json").get(ClientResponse
                    .class);

    String body = response.getEntity(String.class);
    JSONObject jsonObj = new JSONObject(body);

    //println(String.format("Body %s", body));
    int total = jsonObj.getInt("total");
    int availablePages = (int) Math.ceil(total / 100);
    println(String.format("Available pages %d, and total %d", availablePages, total));

    return getMapResult(client, jsonObj, availablePages);
}

Map<String, Map<String, List<String[]>>> getMapResult(Client client, JSONObject jsonObj, int availablePages)
        throws JSONException {
    Map<String, Map<String, List<String[]>>> ruleViolations = new HashMap<>();
    ruleViolations = processResponse(ruleViolations, jsonObj);
    if (availablePages > 1) {
        for (int i = 2; i <= availablePages; i++) {
            println(String.format("Requesting page %d", i))
            WebResource webResource = client.resource(sonarUrl)
                    .queryParam("ps", String.valueOf(100))
                    .queryParam("p", String.valueOf(i));
            ClientResponse response = webResource.accept("application/json")
                    .type("application/json")
                    .get(ClientResponse.class);
            String body = response.getEntity(String.class);
            //println(String.format("paged Body is %s", body))
            JSONObject newBody = new JSONObject(body);
            ruleViolations = processResponse(ruleViolations, newBody);
        }
    }
    return ruleViolations;
}

Map<String, Map<String, List<String[]>>> processResponse(
        Map<String, Map<String, List<String[]>>> ruleViolations, JSONObject jsonObj) throws JSONException {
    JSONArray array = jsonObj.getJSONArray("issues");
    for (int i = 0; i < array.length(); i++) {
        JSONObject issueObj = (JSONObject) array.get(i);
        String projectName = (String) issueObj.get("project");
        String ruleName = (String) issueObj.get("rule");
        String file = (String) issueObj.get("component");
        //println(String.format("IssueObject is %s", issueObj))
        String[] newViolation = getStringsFromResponse(issueObj, file);
        Map<String, List<String[]>> ruleMap = ruleViolations.getOrDefault(projectName, new HashMap<>());
        List<String[]> violations = ruleMap.getOrDefault(ruleName, new ArrayList<>());
        violations.add(newViolation);
        ruleMap.put(ruleName, violations);
        ruleViolations.put(projectName, ruleMap);
        //println(String.format("New Violation for rule %s has been added for project %s", ruleName, projectName))
    }
    return ruleViolations
}

String[] getStringsFromResponse(JSONObject issueObj, String file) throws JSONException {
    String startLine = "";
    String endLine = "";
    String startOffset = "";
    String endOffset = "";
    if (issueObj.has("textRange")) {
        JSONObject textRange = (JSONObject) issueObj.get("textRange");
        startLine = String.valueOf(textRange.get("startLine"));
        endLine = String.valueOf(textRange.get("endLine"));
        if (textRange.has("startOffset")) {
            startOffset = String.valueOf(textRange.get("startOffset"));
        }
        if (textRange.has("endOffset")) {
            endOffset = String.valueOf(textRange.get("endOffset"));
        }
    }
    String message = issueObj.getString("message");
    String type = issueObj.getString("type");
    String project = issueObj.getString("project");
    String subProject = "";
    if (issueObj.has(subProject)) {
        subProject = issueObj.getString("subProject");
    }


    String fileOk = file.replaceAll(subProject + ":", '')
    return [fileOk, startLine, endLine, startOffset, endOffset, message, type]
}


String componentRoots(Map cgProjects) {
    String ret = "componentRoots="
    boolean comma = false;
    cgProjects.each {
        if (comma) {
            ret += new String("," + it.key).encodeURL()
        } else {
            comma = true;
            ret += new String(it.key).encodeURL()
        }
    }
    return ret;
}

def retry(int times = 5, Closure errorHandler = { e -> log.warn(e.message, e) }
          , Closure body) {
    int retries = 0
    def exceptions = []
    while (retries++ < times) {
        try {
            println "Attempt:" + retries
            return body.call()
        } catch (e) {
            exceptions << e
            errorHandler.call(e)
        }
    }
    //throw new Exception("Failed after $times retries")
}
