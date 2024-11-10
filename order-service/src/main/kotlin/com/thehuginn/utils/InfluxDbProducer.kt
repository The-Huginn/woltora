package com.thehuginn.utils

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory
import com.influxdb.client.InfluxDBClientOptions
import com.influxdb.client.WriteApiBlocking
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class InfluxDBProducer {

    @Inject
    @ConfigProperty(name = "influxdb.connectionUrl")
    lateinit var influxUrl: String

    @Inject
    @ConfigProperty(name = "influxdb.data.bucketName")
    lateinit var bucketName: String

    @Inject
    @ConfigProperty(name = "influxdb.orgId")
    lateinit var orgId: String

    @Inject
    @ConfigProperty(name = "influxdb.username")
    lateinit var username: String

    @Inject
    @ConfigProperty(name = "influxdb.password")
    lateinit var password: String

    @Produces
    @ApplicationScoped
    fun createInfluxDBClient(): InfluxDBClient {
        val influxDBClientOptions = InfluxDBClientOptions.builder()
            .url(influxUrl)
            .authenticate(username, password.toCharArray())
            .bucket(bucketName)
            .org(orgId)
            .build()
        val influxDB = InfluxDBClientFactory.create(influxDBClientOptions)
        return influxDB
    }

    @Produces
    @ApplicationScoped
    fun createInfluxDBClientWriteApi(influxDBClient: InfluxDBClient): WriteApiBlocking = influxDBClient.writeApiBlocking
}