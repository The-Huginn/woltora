package com.thehuginn.influx.db.dev.service.deployment;

import io.quarkus.deployment.IsNormal;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.DevServicesResultBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.dev.devservices.GlobalDevServicesConfig;
import java.util.Map;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class InfluxDbDevServiceProcessor {

    private static final String FEATURE = "influx-db-dev-service";
    private static final int SERVICE_PORT = 8086;
    private static final String QUARKUS = "quarkus";
    private static final String QUARKUS_PASSWORD = "quarkus123";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep(onlyIfNot = IsNormal.class, onlyIf = GlobalDevServicesConfig.Enabled.class)
    public DevServicesResultBuildItem createContainer() {
        DockerImageName dockerImageName = DockerImageName.parse("influxdb:2.7.10");
        GenericContainer container = new GenericContainer<>(dockerImageName)
                .withEnv("DOCKER_INFLUXDB_INIT_MODE", "setup")
                .withEnv("DOCKER_INFLUXDB_INIT_USERNAME", QUARKUS)
                .withEnv("DOCKER_INFLUXDB_INIT_PASSWORD", QUARKUS_PASSWORD)
                .withEnv("DOCKER_INFLUXDB_INIT_ORG", QUARKUS)
                .withEnv("DOCKER_INFLUXDB_INIT_BUCKET", QUARKUS)
                .withExposedPorts(SERVICE_PORT)
                .waitingFor(Wait.forLogMessage(".*" + "Starting query controller" + ".*", 1))
                .withReuse(true);

        container.start();

        String newUrl = "http://" + container.getHost() + ":" + container.getMappedPort(SERVICE_PORT);
        Map<String, String> configOverrides = Map.of(
                "influxdb.connectionUrl", newUrl,
                "influxdb.username", QUARKUS,
                "influxdb.password", QUARKUS_PASSWORD,
                "influxdb.orgId", QUARKUS,
                "influxdb.data.bucketName", QUARKUS
        );

        return new DevServicesResultBuildItem.RunningDevService(FEATURE, container.getContainerId(),
                container::close, configOverrides)
                .toBuildItem();
    }

}
