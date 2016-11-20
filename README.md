# Codecamp - Cluj-Napoca - 2016
**Table of Contents**  
- [Description](#description)  
- [Continuous Integration](#ci)  
- [Live Application](#live-application)  
- [References](#references)  
- [Useful Heroku CLI commands](#heroku-cli-commands)  

<a name="description">Description</a>
--
This repo contains the source code of a JEE 7 web application used during the "Creating a CI/CD pipeline for a Java EE application in the cloud" session at [Codecamp Cluj-Napoca](http://cluj.codecamp.ro/) IT event, which took place in Cluj-Napoca on November 19th, 2016.  

<a name="ci">Continuous Integration</a>
--
* snap-ci: [![Build Status](https://snap-ci.com/satrapu/codecamp-cluj-2016/branch/master/build_image)](https://snap-ci.com/satrapu/codecamp-cluj-2016/branch/master)

<a name="live-application">Live Application</a>
-- 
https://codecamp-cluj-2016.herokuapp.com/

<a name="references">References</a>
--
* Heroku Cloud Application Platform
  * Official Site: https://www.heroku.com/home/
  * Pricing: https://www.heroku.com/pricing/
  * Architecture: https://devcenter.heroku.com/categories/heroku-architecture/
  * Limits: https://devcenter.heroku.com/articles/limits/
  * Elements: https://elements.heroku.com/
  * Buildpacks: https://devcenter.heroku.com/articles/buildpacks/
    * Java Buildpack: https://github.com/heroku/heroku-buildpack-java/ 
  * Deployment: https://devcenter.heroku.com/categories/deployment/
  * Pipelines: https://devcenter.heroku.com/articles/pipelines/
  * Heroku CLI: https://devcenter.heroku.com/categories/command-line/
  * Error Codes: https://devcenter.heroku.com/articles/error-codes/
  * Java on Heroku: https://devcenter.heroku.com/categories/java/
    * Customize Maven: https://github.com/heroku/heroku-buildpack-java#customize-maven
    * Heroku Java Support: https://devcenter.heroku.com/articles/java-support/  
  * PostgreSQL on Heroku: https://devcenter.heroku.com/categories/heroku-postgres/
    * Connecting in Java: https://devcenter.heroku.com/articles/heroku-postgresql#connecting-in-java
  * Alternatives:
    * Red Hat OpenShift: https://www.openshift.com/  
    * IBM Blue Mix: http://www.ibm.com/cloud-computing/bluemix/
    * Others
* WildFly Swarm
    * Official Site: http://wildfly-swarm.io/
    * User Guide: https://www.gitbook.com/book/wildfly-swarm/wildfly-swarm-users-guide/details/
    * Generator: http://wildfly-swarm.io/generator/
    * Just Enough App Server with WildFly Swarm presentation by Antonio Goncalves: https://antoniogoncalves.org/2016/07/13/just-enough-app-server-with-wildfly-swarm/
    * Alternatives:
      * Payara Micro: http://www.payara.fish/payara_micro
      * Spring Boot: http://projects.spring.io/spring-boot/
      * Others
* Java Enterprise Edition 7
  * Official Site: http://www.oracle.com/technetwork/java/javaee/overview/index.html
  * JEE7 Tutorial: https://docs.oracle.com/javaee/7/tutorial/
  * Ticket Monster: http://developers.redhat.com/ticket-monster
  * Alternatives:
    * Spring: https://spring.io/
    * Others
* Flyway
    * Official Site: https://flywaydb.org/
    * Documentation: https://flywaydb.org/documentation/
    * Alternatives:
        * Liquibase: http://www.liquibase.org/
        * Others
* Snap CI
    * Official Site: https://snap-ci.com/
    * Documentation: https://docs.snap-ci.com/
    * Alternatives:
        * CircleCI: https://circleci.com/
        * Semaphore: https://semaphoreci.com/
        * Others
        
<a name="heroku-cli-commands">Useful Heroku CLI commands</a>
--
* Run Heroku application on a Windows machine:
```bash
heroku local -f Procfile.windows -e .env -p 6789
```

* Run Heroku application on a macOS machine:
```bash
heroku local -f Procfile.mac -e .env -p 6789
```

* Display all environment variables defined on the Heroku node:
```bash
heroku run printenv --app codecamp-cluj-2016
```

* Display all config vars defined inside the Heroku application:
```bash
heroku config --app codecamp-cluj-2016
```

* Display the details of the Heroku node OS:
```bash
heroku run 'cat /etc/*-release' --app codecamp-cluj-2016
```

* Display the last 250 logged messages and keep monitoring the log:
```bash
heroku logs -t -n 250 --app codecamp-cluj-2016
```

* Copy config var "DATABASE_URL", which contains the URL pointing to the Heroku managed PostgreSQL database, from Heroku application to the local .env file:
```bash
heroku config:get DATABASE_URL -s  >> .env --app codecamp-cluj-2016
```
