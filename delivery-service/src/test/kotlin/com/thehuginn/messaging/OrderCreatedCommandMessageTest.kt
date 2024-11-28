package com.thehuginn.messaging

import com.thehuginn.messaging.dto.AssignDeliveryEventMessage
import com.thehuginn.messaging.dto.OrderCreatedCommandMessage
import com.thehuginn.messaging.dto.RestaurantCommandMessage
import com.thehuginn.repository.MessageRepository.Message.ASSIGN_DELIVERY
import com.thehuginn.repository.MessageRepository.Message.ORDER_CREATED
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.mutiny.Multi
import jakarta.inject.Inject
import java.math.BigDecimal
import java.util.UUID
import java.util.concurrent.TimeUnit.SECONDS
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.junit.jupiter.api.Test

@QuarkusTest
class OrderCreatedCommandMessageTest {

    @Inject
    @Channel(ORDER_CREATED)
    lateinit var emitter: Emitter<OrderCreatedCommandMessage>

    @Channel(ASSIGN_DELIVERY)
    lateinit var messages: Multi<AssignDeliveryEventMessage>

    @Test
    fun `should assign delivery when message received`() {
        val receivedMessages = mutableListOf<AssignDeliveryEventMessage>()
        messages.subscribe().with { receivedMessages.add(it) }

        emitter.send(
            OrderCreatedCommandMessage(
                orderId = UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"),
                restaurant = RestaurantCommandMessage(
                    id = UUID.fromString("f2c1b18c-e918-41d8-82fb-1bbfd930320f"),
                    latitude = BigDecimal("50"),
                    longitude = BigDecimal("14")
                )
            )
        )

        await().atMost(6, SECONDS).until { receivedMessages.isNotEmpty() }

        assertThat(receivedMessages).satisfiesExactlyInAnyOrder({
            assertThat(it.orderId).isEqualTo(UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"))
            assertThat(it.driverId).isEqualTo(UUID.fromString("7272e01d-3f11-473c-9462-5f62edea852c"))
        })
    }
}
