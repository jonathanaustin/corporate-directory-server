# Corporate Directory Server
Administer your corporate structure and contact details.

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
