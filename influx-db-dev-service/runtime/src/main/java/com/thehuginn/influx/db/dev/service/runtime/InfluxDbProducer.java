package com.thehuginn.influx.db.dev.service.runtime;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class InfluxDbProducer {

    private InfluxDBClientOptions options;

    @Produces
    @ApplicationScoped
    public InfluxDBClient createInfluxDbClient() {
        return InfluxDBClientFactory.create(options);
    }

    @Produces
    @RequestScoped
    public QueryApi createQueryApi(InfluxDBClient client) {
        return client.getQueryApi();
    }

    @Inject
    public void setConfig(InfluxDbDevServiceConfig config) {
        options = InfluxDBClientOptions.builder()
                .url(config.connectionUrl())
                .authenticate(config.username(), config.password().toCharArray())
                .org(config.orgId())
                .bucket(config.dataBucketName())
                .build();
    }

}
