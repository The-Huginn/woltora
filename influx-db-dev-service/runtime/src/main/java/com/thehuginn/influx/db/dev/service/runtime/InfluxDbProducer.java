package com.thehuginn.influx.db.dev.service.runtime;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import com.influxdb.client.WriteApi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class InfluxDbProducer {

    private InfluxDbDevServiceConfig config;

    @Produces
    @ApplicationScoped
    public InfluxDBClient createInfluxDbClient() {
        InfluxDBClientOptions options = InfluxDBClientOptions.builder()
                .url(config.connectionUrl())
                .authenticate(config.username(), config.password().toCharArray())
                .org(config.orgId())
                .bucket(config.dataBucketName())
                .build();

        return InfluxDBClientFactory.create(options);
    }

    @Produces
    @ApplicationScoped
    public WriteApi createWriteApi(InfluxDBClient client) {
        return client.getWriteApi();
    }

    @Inject
    public void setConfig(InfluxDbDevServiceConfig config) {
        this.config = config;
    }

}
