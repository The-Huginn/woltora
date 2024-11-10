package com.thehuginn.messaging

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import com.thehuginn.MessagingTest
import com.thehuginn.messaging.dto.UpdateOrderLocationCommandMessage
import com.thehuginn.repository.MessageRepository.Message.UPDATE_LOCATION
import io.quarkus.test.junit.QuarkusTest
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.lang.Thread.sleep
import java.time.Instant
import java.util.UUID.randomUUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@QuarkusTest
class UpdateOrderLocationCommandMessageTest : MessagingTest() {

    @Inject
    @field:Default
    lateinit var influxDbClient: InfluxDBClient

    @Test
    fun `should update order location when message received`() {
        connector.source<UpdateOrderLocationCommandMessage>(UPDATE_LOCATION).send(
            UpdateOrderLocationCommandMessage(
                orderId = randomUUID(),
                location = "Test Location"
            )
        ).complete()

        sleep(100)

        assertThat(influxDbClient.queryApi.query("from(bucket:\"quarkus\") |> range(start: -10m)")).satisfies( {
            assertThat(it[0].records[0].value).isEqualTo(
                "Test Location"
            )
        })
    }
}