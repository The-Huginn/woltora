package com.thehuginn.messaging

import com.influxdb.client.InfluxDBClient
import com.thehuginn.MessagingTest
import com.thehuginn.messaging.dto.UpdateOrderLocationCommandMessage
import com.thehuginn.repository.MessageRepository.Message.UPDATE_LOCATION
import com.thehuginn.utils.QuarkusTestWithSql
import com.thehuginn.utils.Sql
import io.quarkus.test.junit.QuarkusTest
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.lang.Thread.sleep
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestWithSql
class UpdateTest2 : MessagingTest() {

    @Inject
    @field:Default
    lateinit var influxDbClient: InfluxDBClient

    @Inject
    @field:Default
    lateinit var updateOrderLocationCommandConsumer: UpdateOrderLocationCommandConsumer

    @Test
    @Sql(["sql/simple-order.sql"])
    fun `should update order location when message received`() {
//        connector.source<UpdateOrderLocationCommandMessage>(UPDATE_LOCATION).send(
//            UpdateOrderLocationCommandMessage(
//                orderId = UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"),
//                location = "Test Location"
//            )
//        )
//            .runOnVertxContext(true)
//            .complete()
//
//        sleep(1000)

        updateOrderLocationCommandConsumer.process(
            UpdateOrderLocationCommandMessage(
                orderId = UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"),
                location = "Test Location"
            )
        )
            .await().indefinitely()

        assertThat(influxDbClient.queryApi.query("from(bucket:\"quarkus\") |> range(start: -10m)")).anySatisfy {
            assertThat(it.records).anySatisfy { record ->
                assertThat(record.value).isEqualTo(
                    "Test Location"
                )
            }
        }
    }
}