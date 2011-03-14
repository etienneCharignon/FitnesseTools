package fitnesse.tools;

import static java.util.Arrays.asList;

import java.util.Set;
import java.util.TreeSet;

public class ClasspathBuilder {

    private static final String M2_FITNESSE_SIMPLE = "!path \\${MAVEN_REPO_SIMPLE}";
    private static final String M2_FITNESSE = "!path \\${MAVEN_REPO}";
    private static final String M2_PAR_DEV_01 = "!path \\${MAVEN_PARDEV01}";
    private static final String M2_LOCAL = "D:/m2-repo";

    private interface JarPathFilter {
        boolean filter(String jarPath);
    }

    // Pour generer la contante JARS_FROM_MAVEN_CMD,
    // lancer la commande suivante (mvn dependency:build-classpath) depuis le repertoire racine de maven
    // + recopier les dépendences de l'appli.
    // + replace \ by /
    private static final String VERSION = "1.10.0-SNAPSHOT";
    private static final String JARS_FROM_MAVEN_CMD = "D:/m2-repo/antlr/antlr/2.7.6/antlr-2.7.6.jar;D:/m2-repo/aopalliance/aopalliance/1.0/aopalliance-1.0.jar;D:/m2-repo/bouncycastle/bcmail-jdk14/138/bcmail-jdk14-138.jar;D:/m2-repo/bouncycastle/bcprov-jdk14/138/bcprov-jdk14-138.jar;D:/m2-repo/cglib/cglib-nodep/2.2/cglib-nodep-2.2.jar;D:/m2-repo/com/ibm/icu/icu4j/2.6.1/icu4j-2.6.1.jar;D:/m2-repo/com/lowagie/itext/2.1.7/itext-2.1.7.jar;D:/m2-repo/com/opensymphony/xwork-core/2.1.6/xwork-core-2.1.6.jar;C:/Program Files/Java/jdk1.5.0_22/jre/../lib/tools.jar;D:/m2-repo/com/thoughtworks/xstream/xstream/1.3.1/xstream-1.3.1.jar;D:/m2-repo/commons-beanutils/commons-beanutils/1.7.0/commons-beanutils-1.7.0.jar;D:/m2-repo/commons-collections/commons-collections/3.1/commons-collections-3.1.jar;D:/m2-repo/commons-dbcp/commons-dbcp/1.2.2/commons-dbcp-1.2.2.jar;D:/m2-repo/commons-digester/commons-digester/1.8/commons-digester-1.8.jar;D:/m2-repo/commons-fileupload/commons-fileupload/1.2.1/commons-fileupload-1.2.1.jar;D:/m2-repo/commons-io/commons-io/1.3.2/commons-io-1.3.2.jar;D:/m2-repo/commons-lang/commons-lang/2.4/commons-lang-2.4.jar;D:/m2-repo/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar;D:/m2-repo/commons-logging/commons-logging-api/1.1/commons-logging-api-1.1.jar;D:/m2-repo/commons-pool/commons-pool/1.3/commons-pool-1.3.jar;D:/m2-repo/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar;D:/m2-repo/fr/myCompany/myApp/dao/1.10.0-SNAPSHOT/dao-1.10.0-SNAPSHOT-tests.jar;D:/m2-repo/fr/myCompany/myApp/dao/1.10.0-SNAPSHOT/dao-1.10.0-SNAPSHOT.jar;D:/m2-repo/fr/myCompany/myApp/domain/1.10.0-SNAPSHOT/domain-1.10.0-SNAPSHOT.jar;D:/m2-repo/fr/myCompany/myApp/interfaces/1.10.0-SNAPSHOT/interfaces-1.10.0-SNAPSHOT-tests.jar;D:/m2-repo/fr/myCompany/myApp/interfaces/1.10.0-SNAPSHOT/interfaces-1.10.0-SNAPSHOT.jar;D:/m2-repo/fr/myCompany/myApp/services/1.10.0-SNAPSHOT/services-1.10.0-SNAPSHOT.jar;D:/m2-repo/javassist/javassist/3.4.GA/javassist-3.4.GA.jar;D:/m2-repo/javax/activation/activation/1.1/activation-1.1.jar;D:/m2-repo/javax/mail/mail/1.4/mail-1.4.jar;D:/m2-repo/javax/servlet/servlet-api/2.4/servlet-api-2.4.jar;D:/m2-repo/javax/transaction/jta/1.1/jta-1.1.jar;D:/m2-repo/jaxen/jaxen/1.1.1/jaxen-1.1.1.jar;D:/m2-repo/jdom/jdom/1.0/jdom-1.0.jar;D:/m2-repo/junit/junit/4.4/junit-4.4.jar;D:/m2-repo/junit-addons/junit-addons/1.4/junit-addons-1.4.jar;D:/m2-repo/log4j/log4j/1.2.15/log4j-1.2.15.jar;D:/m2-repo/net/sf/ehcache/ehcache/1.6.2/ehcache-1.6.2.jar;D:/m2-repo/ognl/ognl/2.7.3/ognl-2.7.3.jar;D:/m2-repo/opensymphony/ognl/2.6.11/ognl-2.6.11.jar;D:/m2-repo/org/apache/struts/struts2-convention-plugin/2.1.8.1/struts2-convention-plugin-2.1.8.1.jar;D:/m2-repo/org/apache/struts/struts2-core/2.1.8.1/struts2-core-2.1.8.1.jar;D:/m2-repo/org/apache/struts/struts2-spring-plugin/2.1.8.1/struts2-spring-plugin-2.1.8.1.jar;D:/m2-repo/org/apache/struts/struts2-tiles-plugin/2.1.8.1/struts2-tiles-plugin-2.1.8.1.jar;D:/m2-repo/org/apache/tiles/tiles-api/2.0.6/tiles-api-2.0.6.jar;D:/m2-repo/org/apache/tiles/tiles-core/2.0.6/tiles-core-2.0.6.jar;D:/m2-repo/org/apache/tiles/tiles-jsp/2.0.6/tiles-jsp-2.0.6.jar;D:/m2-repo/org/aspectj/aspectjrt/1.6.1/aspectjrt-1.6.1.jar;D:/m2-repo/org/aspectj/aspectjweaver/1.6.1/aspectjweaver-1.6.1.jar;D:/m2-repo/org/bouncycastle/bcmail-jdk14/1.38/bcmail-jdk14-1.38.jar;D:/m2-repo/org/bouncycastle/bcprov-jdk14/1.38/bcprov-jdk14-1.38.jar;D:/m2-repo/org/bouncycastle/bctsp-jdk14/1.38/bctsp-jdk14-1.38.jar;D:/m2-repo/org/dbunit/dbunit/2.4.7/dbunit-2.4.7.jar;D:/m2-repo/org/freemarker/freemarker/2.3.13/freemarker-2.3.13.jar;D:/m2-repo/org/hibernate/ejb3-persistence/1.0.2.GA/ejb3-persistence-1.0.2.GA.jar;D:/m2-repo/org/hibernate/hibernate-annotations/3.4.0.GA/hibernate-annotations-3.4.0.GA.jar;D:/m2-repo/org/hibernate/hibernate-cglib-repack/2.1_3/hibernate-cglib-repack-2.1_3.jar;D:/m2-repo/org/hibernate/hibernate-commons-annotations/3.1.0.GA/hibernate-commons-annotations-3.1.0.GA.jar;D:/m2-repo/org/hibernate/hibernate-core/3.3.1.GA/hibernate-core-3.3.1.GA.jar;D:/m2-repo/org/json/json/20080701/json-20080701.jar;D:/m2-repo/org/mockito/mockito-all/1.8.0/mockito-all-1.8.0.jar;D:/m2-repo/org/objenesis/objenesis/1.1/objenesis-1.1.jar;D:/m2-repo/org/powermock/powermock-core/1.3/powermock-core-1.3.jar;D:/m2-repo/org/powermock/api/powermock-api-mockito/1.3/powermock-api-mockito-1.3.jar;D:/m2-repo/org/powermock/api/powermock-api-support/1.3/powermock-api-support-1.3.jar;D:/m2-repo/org/powermock/modules/powermock-module-junit4/1.3/powermock-module-junit4-1.3.jar;D:/m2-repo/org/powermock/modules/powermock-module-junit4-common/1.3/powermock-module-junit4-common-1.3.jar;D:/m2-repo/org/powermock/reflect/powermock-reflect/1.3/powermock-reflect-1.3.jar;D:/m2-repo/org/slf4j/slf4j-api/1.5.6/slf4j-api-1.5.6.jar;D:/m2-repo/org/slf4j/slf4j-log4j12/1.5.2/slf4j-log4j12-1.5.2.jar;D:/m2-repo/org/springframework/spring-aop/2.5.6/spring-aop-2.5.6.jar;D:/m2-repo/org/springframework/spring-aspects/2.5.6/spring-aspects-2.5.6.jar;D:/m2-repo/org/springframework/spring-beans/2.5.6/spring-beans-2.5.6.jar;D:/m2-repo/org/springframework/spring-context/2.5.6/spring-context-2.5.6.jar;D:/m2-repo/org/springframework/spring-core/2.5.6/spring-core-2.5.6.jar;D:/m2-repo/org/springframework/spring-jdbc/2.5.6/spring-jdbc-2.5.6.jar;D:/m2-repo/org/springframework/spring-orm/2.5.6/spring-orm-2.5.6.jar;D:/m2-repo/org/springframework/spring-test/2.5.6/spring-test-2.5.6.jar;D:/m2-repo/org/springframework/spring-tx/2.5.6/spring-tx-2.5.6.jar;D:/m2-repo/org/springframework/spring-web/2.5.6/spring-web-2.5.6.jar;D:/m2-repo/postgresql/postgresql/8.3-603.jdbc3/postgresql-8.3-603.jdbc3.jar;D:/m2-repo/xalan/xalan/2.6.0/xalan-2.6.0.jar;D:/m2-repo/xerces/xercesImpl/2.6.2/xercesImpl-2.6.2.jar;D:/m2-repo/xerces/xmlParserAPIs/2.6.2/xmlParserAPIs-2.6.2.jar;D:/m2-repo/xml-apis/xml-apis/1.3.02/xml-apis-1.3.02.jar;D:/m2-repo/xmlunit/xmlunit/1.3/xmlunit-1.3.jar;D:/m2-repo/xom/xom/1.0/xom-1.0.jar;D:/m2-repo/xpp3/xpp3_min/1.1.4c/xpp3_min-1.1.4c.jar";

    private static final Set<String> JARS_CLASSPATH = new TreeSet<String>();
    static {
        JARS_CLASSPATH.addAll(asList(JARS_FROM_MAVEN_CMD.split(";")));
    }

    public static void main(String[] args) {
        JarPathFilter allButMyCompany = new JarPathFilter() {

            public boolean filter(String jarPath) {
                return jarPath.contains("myCompany") || jarPath.contains("C:/Program Files");
            }
        };

        JarPathFilter myCompanyJars = new JarPathFilter() {

            public boolean filter(String jarPath) {
                return !jarPath.contains("myCompany") || jarPath.endsWith("-tests.jar");
            }
        };

        // construire le jar suivant à la main
        // cd D:\workspace\myApp\myCompany\target\classes
        // jar -cf myApp.jar *
        System.out.println("!path myApp.jar");

        printFitnesseClasspathForMyCompanyTestJar("dao", "interfaces", "services", "myApp");
        printFitnesseClasspath(M2_PAR_DEV_01, myCompanyJars);

        printFitnesseClasspath(M2_FITNESSE, allButMyCompany);
        printFitnesseClasspath(M2_FITNESSE_SIMPLE, allButMyCompany);
        printFitnesseClasspath(M2_PAR_DEV_01, allButMyCompany);
    }

    private static void printFitnesseClasspathForMyCompanyTestJar(String... mavenModules) {
        for (String module : mavenModules) {
            System.out.println(String.format("!path ${MAVEN_PARDEV01}/fr/myCompany/myApp/%s/%s/%s-%s-tests.jar", module,
                    VERSION, module, VERSION));
        }
    }

    private static void printFitnesseClasspath(String m2Root, JarPathFilter filter) {
        for (String jarPath : JARS_CLASSPATH) {
            if (filter.filter(jarPath)) {
                continue;
            }
            System.out.println(jarPath.replaceAll(M2_LOCAL, m2Root));
        }
        System.out.println();
    }

}