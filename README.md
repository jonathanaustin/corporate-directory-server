# Corporate Directory Server
Administer your corporate structure and contact details.

## Status
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ee22a2d561da4403bdd407dc02698f5c)](https://www.codacy.com/app/BorderTech/corporate-directory-server?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bordertech/corporate-directory-server&amp;utm_campaign=Badge_Grade)

## Live Demo
A live [demo](http://bordertech-corpdir.herokuapp.com/admin) is available.

The [REST API](http://bordertech-corpdir.herokuapp.com/swagger-ui/index.html?url=/api/swagger) is also available.

## Build and Run Locally
If you wish to build the application you will need [Apache Maven](https://maven.apache.org/) installed. Minimum requirements are `Maven 3.2.2` and `Java 7`.

Follow these commands to fetch the source, build and run:

1. `git clone https://github.com/bordertech/corporate-directory-server.git` (first time only)
2. `cd corporate-directory-server`
3. `mvn install`
4. `mvn jetty:run`
5.  Access at [http://localhost:8080/admin](http://localhost:8080/admin)
