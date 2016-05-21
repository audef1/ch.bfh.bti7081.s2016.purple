HealthVisitor
==============

We are building a tool to make Health Visitors life easier.


Logging
========
Every class should have a logger, to have specific logs.
We use log4j2 to for logs that means for you do the following in every class:

``` java

public class xyz {
    private static final Logger logger = LogManager.getLogger(xyz.class);
}

```

Then you can write logs on your prefered loglevel with:
```java
    logger.<loglevel>("whatever");
```

see online documentation: http://logging.apache.org/log4j/2.x/manual/api.html

Persistence Layer
========
JPA and EclipseLink
JPA tutorial: http://www.tutorialspoint.com/jpa

EclipseLink tutorial:
https://www.eclipse.org/eclipselink/documentation/2.6/

Workflow
========

To compile the entire project, run "_mvn install_".
To compile the entire project, run "_mvn vaadin:compile_".

To run the application, run "_mvn jetty:run_" and open http://localhost:8080/ .

Debugging client side code
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean package"
- test the war file with "mvn jetty:run-war"

Developing a theme using the runtime compiler
-------------------------

When developing the theme, Vaadin can be configured to compile the SASS based
theme at runtime in the server. This way you can just modify the scss files in
your IDE and reload the browser to see changes.

To use the runtime compilation, open pom.xml and comment out the compile-theme
goal from vaadin-maven-plugin configuration. To remove a possibly existing
pre-compiled theme, run "mvn clean package" once.

When using the runtime compiler, running the application in the "run" mode
(rather than in "debug" mode) can speed up consecutive theme compilations
significantly.

It is highly recommended to disable runtime compilation for production WAR files.
