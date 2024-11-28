package com.thehuginn.messaging

import com.thehuginn.enums.OrderStatus.IN_DELIVERY
import com.thehuginn.messaging.dto.command.AssignDeliveryCommandMessage
import com.thehuginn.repository.MessageRepository.Message.ASSIGN_DELIVERY
import com.thehuginn.repository.OrderRepository
import com.thehuginn.sql.QuarkusTestWithSql
import com.thehuginn.sql.Sql
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.smallrye.mutiny.Uni
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import java.time.Duration
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
    @Sql(["sql/simple-restaurant.sql", "sql/simple-order.sql"])
    @RunOnVertxContext
    fun `should assign delivery when message received`() {
        emitter.send(
            AssignDeliveryCommandMessage(
                orderId = UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"),
                driverId = UUID.fromString("7272e01d-3f11-473c-9462-5f62edea852c")
            )
        )

        Uni.createFrom().voidItem().onItem().delayIt().by(Duration.ofSeconds(1)).subscribe().with {
            orderRepository.listAll().subscribe().with { orders ->
                assertThat(orders).satisfiesExactlyInAnyOrder({
                    assertThat(it.id).isEqualTo(UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338"))
                    assertThat(it.items).satisfiesExactlyInAnyOrder({ item ->
                        assertThat(item).isEqualTo(
                            UUID.fromString(
                                "f1e98890-74d1-4800-8347-7f2e68fc9bfc"
                            )
                        )
                    })
                    assertThat(it.userId).isEqualTo(UUID.fromString("5ff13a5c-ef93-4096-8507-21ab93d1f894"))
                    assertThat(it.restaurant.id).isEqualTo(UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed01a"))
                    assertThat(it.status).isEqualTo(IN_DELIVERY)
                })
            }
        }
    }
}