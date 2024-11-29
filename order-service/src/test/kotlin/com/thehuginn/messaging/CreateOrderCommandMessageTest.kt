package com.thehuginn.messaging

import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.messaging.dto.command.CreateOrderCommandMessage
import com.thehuginn.messaging.dto.event.OrderCreatedEventMessage
import com.thehuginn.repository.MessageRepository.Message.CREATE_ORDER
import com.thehuginn.repository.MessageRepository.Message.ORDER_CREATED
import com.thehuginn.repository.OrderRepository
import com.thehuginn.sql.QuarkusTestWithSql
import com.thehuginn.sql.Sql
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.smallrye.mutiny.Multi
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
class CreateOrderCommandMessageTest {

    @Inject
    @field:Default
    lateinit var orderRepository: OrderRepository

    @Channel(ORDER_CREATED)
    lateinit var messages: Multi<OrderCreatedEventMessage>

    @Inject
    @Channel(CREATE_ORDER)
    lateinit var emitter: Emitter<CreateOrderCommandMessage>

    @Test
    @Sql(["sql/simple-restaurant.sql"])
    @RunOnVertxContext
    fun `should create simple order when message received`() {
        val receivedMessages = mutableListOf<OrderCreatedEventMessage>()
        messages.subscribe().with { message ->
            receivedMessages.add(message)
        }

        emitter.send(
            CreateOrderCommandMessage(
                items = listOf(UUID.fromString("63e01f7a-aded-4353-acce-095cd8ed8f18")),
                userId = UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84"),
                addressId = UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed23c"),
                restaurantId = UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed01a")
            )
        )

        Uni.createFrom().voidItem().onItem().delayIt().by(Duration.ofSeconds(1)).subscribe().with {
            assertThat(receivedMessages).satisfiesExactlyInAnyOrder({
                assertThat(it.restaurant.id).isEqualTo(UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed01a"))
                assertThat(it.restaurant.latitude).isEqualByComparingTo("50.07")
                assertThat(it.restaurant.longitude).isEqualByComparingTo("14.43")
            })

            orderRepository.listAll().subscribe().with { orders ->
                assertThat(orders).satisfiesExactlyInAnyOrder(
                    {
                        assertThat(it.id).isNotNull()
                        assertThat(it.items).satisfiesExactlyInAnyOrder({ item ->
                            assertThat(item).isEqualTo(UUID.fromString("63e01f7a-aded-4353-acce-095cd8ed8f18"))
                        })
                        assertThat(it.userId).isEqualTo(UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84"))
                        assertThat(it.addressId).isEqualTo(UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed23c"))
                        assertThat(it.restaurant).satisfies({ restaurant ->
                            assertThat(restaurant.id).isEqualTo(UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed01a"))
                            assertThat(restaurant.name).isEqualTo("Woltora's Franchise")
                            assertThat(restaurant.latitude).isEqualByComparingTo("50.07")
                            assertThat(restaurant.longitude).isEqualByComparingTo("14.43")
                        })
                        assertThat(it.status).isEqualTo(CREATED)
                    }
                )
            }
        }
    }
}