package com.thehuginn.messaging

import com.thehuginn.messaging.MessageRepository.Message.CREATE_ORDER
import com.thehuginn.messaging.dto.CreateOrderCommandMessage
import com.thehuginn.repositories.OrderRepository
import com.thehuginn.utils.KafkaTestResource
import io.quarkus.test.TestTransaction
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.reactive.messaging.memory.InMemoryConnector
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.microprofile.reactive.messaging.spi.Connector
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(KafkaTestResource::class)
class CreateOrderCommandMessageTest {

    @Inject
    @field:Connector("smallrye-in-memory")
    lateinit var connector: InMemoryConnector

    @Inject
    @field:Default
    lateinit var orderRepository: OrderRepository

    @Test
    @TestTransaction
    fun `should create simple order when message received`() {
        connector.source<CreateOrderCommandMessage>(CREATE_ORDER).send(CreateOrderCommandMessage(
            items = listOf(UUID.fromString("63e01f7a-aded-4353-acce-095cd8ed8f18")),
            userId = UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84"),
            restaurantId = UUID.fromString("69b2a618-84d7-45e9-b082-96db140f9973")
        )).complete()

        assertThat(orderRepository.findAll()).satisfiesExactlyInAnyOrder(
            {
                assertThat(it.id).isNotNull()
                assertThat(it.items).satisfiesExactlyInAnyOrder( { item -> assertThat(item).isEqualTo(UUID.fromString("63e01f7a-aded-4353-acce-095cd8ed8f18")) })
                assertThat(it.userId).isEqualTo(UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84"))
                assertThat(it.restaurantId).isEqualTo(UUID.fromString("69b2a618-84d7-45e9-b082-96db140f9973"))
            }
        )
    }
}