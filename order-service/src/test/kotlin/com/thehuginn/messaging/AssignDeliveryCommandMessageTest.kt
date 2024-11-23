package com.thehuginn.messaging

import com.thehuginn.enums.OrderStatus.IN_DELIVERY
import com.thehuginn.messaging.dto.AssignDeliveryCommandMessage
import com.thehuginn.repository.MessageRepository.Message.ASSIGN_DELIVERY
import com.thehuginn.repository.OrderRepository
import com.thehuginn.sql.QuarkusTestWithSql
import com.thehuginn.sql.Sql
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
@QuarkusTestWithSql
class AssignDeliveryCommandMessageTest {

    @Inject
    @field:Default
    lateinit var orderRepository: OrderRepository

    @Inject
    @Channel(ASSIGN_DELIVERY)
    lateinit var emitter: Emitter<AssignDeliveryCommandMessage>

    @Test
    @Sql(["sql/simple-order.sql"])
    fun `should assign delivery when message received`() {
        emitter.send(
            AssignDeliveryCommandMessage(UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"))
        )

        sleep(1000)

        assertThat(orderRepository.listAll()).satisfiesExactlyInAnyOrder({
            assertThat(it.id).isEqualTo(UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"))
            assertThat(it.items).satisfiesExactlyInAnyOrder({ item -> assertThat(item).isEqualTo(UUID.fromString("f1e98890-74d1-4800-8347-7f2e68fc9bfc")) })
            assertThat(it.userId).isEqualTo(UUID.fromString("5ff13a5c-ef93-4096-8507-21ab93d1f894"))
            assertThat(it.restaurantId).isEqualTo(UUID.fromString("6085ce48-da17-42ab-bef7-ee34f60f0762"))
            assertThat(it.status).isEqualTo(IN_DELIVERY)
        })
    }
}