cd influx-db-dev-service && mvn clean install && cd -
cd quarkus-sql-testing && gradle clean build && gradle publishToMavenLocal && cd -
