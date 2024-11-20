package com.thehuginn.influx.db.dev.service.runtime;

import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;

import static io.quarkus.runtime.annotations.ConfigPhase.RUN_TIME;

@ConfigMapping(prefix = "quarkus.influxdb")
@ConfigRoot(phase = RUN_TIME)
public interface InfluxDbDevServiceConfig {

    /**
     * Connection Url to InfluxDB
     */
    String connectionUrl();
    /**
     * Username used for authentication
     */
    String username();
    /**
     * Password used for authentication
     */
    String password();
    /**
     * Organization ID
     */
    String orgId();
    /**
     * Bucket name
     */
    String dataBucketName();

}
