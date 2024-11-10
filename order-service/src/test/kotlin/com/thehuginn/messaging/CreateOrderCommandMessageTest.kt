package com.thehuginn.messaging

import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.messaging.dto.CreateOrderCommandMessage
import com.thehuginn.repository.MessageRepository.Message.CREATE_ORDER
import com.thehuginn.repository.OrderRepository
import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.lang.Thread.sleep
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.junit.jupiter.api.Test

@QuarkusTest
class CreateOrderCommandMessageTest {

    @Inject
    @field:Default
    lateinit var orderRepository: OrderRepository

    @Inject
    @Channel(CREATE_ORDER)
    lateinit var emitter: Emitter<CreateOrderCommandMessage>

    @Test
    @TestTransaction
    fun `should create simple order when message received`() {
        emitter.send(
            CreateOrderCommandMessage(
                items = listOf(UUID.fromString("63e01f7a-aded-4353-acce-095cd8ed8f18")),
                userId = UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84"),
                restaurantId = UUID.fromString("69b2a618-84d7-45e9-b082-96db140f9973")
            )
        )

        sleep(1000)

        assertThat(orderRepository.listAll()).satisfiesExactlyInAnyOrder(
            {
                assertThat(it.id).isNotNull()
                assertThat(it.items).satisfiesExactlyInAnyOrder({ item -> assertThat(item).isEqualTo(UUID.fromString("63e01f7a-aded-4353-acce-095cd8ed8f18")) })
                assertThat(it.userId).isEqualTo(UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84"))
                assertThat(it.restaurantId).isEqualTo(UUID.fromString("69b2a618-84d7-45e9-b082-96db140f9973"))
                assertThat(it.status).isEqualTo(CREATED)
            }
        )
    }
}