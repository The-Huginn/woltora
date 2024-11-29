package com.thehuginn.messaging

import com.influxdb.client.QueryApi
import com.thehuginn.messaging.dto.UpdateOrderLocationCommandMessage
import com.thehuginn.repository.MessageRepository.Message.UPDATE_LOCATION
import com.thehuginn.service.impl.DefaultOrderLocationService.Companion.INFLUX_QUERY
import io.quarkus.test.junit.QuarkusTest
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.lang.Thread.sleep
import java.time.Instant.now
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.junit.jupiter.api.Test

@QuarkusTest
class UpdateOrderLocationCommandMessageTest {

    @Inject
    @field:Default
    lateinit var queryApi: QueryApi

    @Inject
    @Channel(UPDATE_LOCATION)
    lateinit var emitter: Emitter<UpdateOrderLocationCommandMessage>

    @Test
    fun `should update order location when message received`() {
        val instant = now()
        emitter.send(
            UpdateOrderLocationCommandMessage(
                orderId = UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"),
                location = "Random XYZ"
            )
        )

        sleep(1000)

        assertThat(queryApi.query(INFLUX_QUERY)).anySatisfy {
            assertThat(it.records).anySatisfy { record ->
                assertThat(record.time).isAfter(instant)
                assertThat(record.values).containsEntry("orderId", "278ba540-d2e7-4f0c-862d-a6b6e5180338")
                assertThat(record.values).containsEntry("_value", "Random XYZ")
            }
        }
    }
}