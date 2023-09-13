# Instructions 

* Install eclipse
* [Get the code from git](https://github.com/phil-rice-HCL/HelloDataNucleusJBoss.git)
* Import into eclipse
* Ensure the SDK is Java 8
* Open up a terminal at the root
* Type `mvn clean install`
* Type `mvn datanucleus:enhance -pl helloworld`
* Setup a server with JBoss 7.2.2 [Wildfly]
    * When asked if you want to add a program, add helloworld.war
* Update the applicationinsights.json connection string
* Run the server
* If not opened automatically, on the browser go to `http://127.0.0.1:8080/helloworld/HelloWorld`



# VM Args 
```

"-Dprogram.name=JBossTools: Red Hat JBoss EAP 7.2" -server -Xms1303m -Xmx1303m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Dorg.jboss.resolver.warning=true -Djava.net.preferIPv4Stack=true -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true "-Dorg.jboss.boot.log.file=C:\Users\diana\Downloads\EAP-7.2.0\standalone\log\boot.log" "-Dlogging.configuration=file:C:\Users\diana\Downloads\EAP-7.2.0\standalone\configuration\logging.properties" "-Djboss.home.dir=C:\Users\diana\Downloads\EAP-7.2.0" -Dorg.jboss.logmanager.nocolor=true -Djboss.bind.address.management=localhost -javaagent:C:\Users\diana\git\HelloDataNucleusJBoss\helloworld\applicationinsights-agent-3.4.16.jar -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Xbootclasspath/p:C:\Users\diana\Downloads\EAP-7.2.0/modules/system/layers/base/org/jboss/logmanager/main/jboss-logmanager-2.1.5.Final-redhat-00001.jar:C:\Users\diana\Downloads\EAP-7.2.0/modules/system/layers/base/org/slf4j/impl/main/slf4j-jboss-logmanager-1.0.3.GA-redhat-2.jar:/modules/system/layers/base/org/wildfly/common/main/wildfly-common-1.4.0.Final-redhat-1.jar

```
* The folder for JBoss(C:\Users\diana\Downloads\EAP-7.2.0) should be replaced with own location
* The javaagent location(C:\Users\diana\git\HelloDataNucleusJBoss\helloworld\applicationinsights-agent-3.4.16.jar) should also be set to proper location

# Program Args

```

-mp "C:\Users\diana\Downloads\EAP-7.2.0\modules" org.jboss.as.standalone -b localhost --server-config=standalone.xml -Djboss.server.base.dir=C:\Users\diana\Downloads\EAP-7.2.0\standalone 

```

* These were the default program arguments included for completeness

# Expected Behaviour

* In the Azure Application Insights we expect to see "SELECT * FROM HelloWorld"

# Actual Behaviour

* In the Azure Application Insights we see "h2 | test"


