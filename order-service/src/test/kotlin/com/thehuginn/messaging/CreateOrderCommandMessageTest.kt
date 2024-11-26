package com.thehuginn.messaging

import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.messaging.dto.command.CreateOrderCommandMessage
import com.thehuginn.repository.MessageRepository.Message.CREATE_ORDER
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
class CreateOrderCommandMessageTest {

    @Inject
    @field:Default
    lateinit var orderRepository: OrderRepository

//    @Channel(ORDER_CREATED)
//    lateinit var messages: Multi<OrderCreatedEventMessage>

    @Inject
    @Channel(CREATE_ORDER)
    lateinit var emitter: Emitter<CreateOrderCommandMessage>

    @Test
    @Sql(["sql/simple-restaurant.sql"])
    fun `should create simple order when message received`() {
        emitter.send(
            CreateOrderCommandMessage(
                items = listOf(UUID.fromString("63e01f7a-aded-4353-acce-095cd8ed8f18")),
                userId = UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84"),
                restaurantId = UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed01a")
            )
        )

        sleep(1000)

//        val messageReceived = messages.select().first().collect().asList().await().indefinitely()
//        assertThat(messageReceived).satisfiesExactlyInAnyOrder({
//            assertThat(messageReceived.first().restaurant.id).isEqualTo(UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed01a"))
//            assertThat(messageReceived.first().restaurant.latitude).isEqualByComparingTo("50.06533754")
//            assertThat(messageReceived.first().restaurant.longitude).isEqualByComparingTo("14.42560964")
//        })

        assertThat(orderRepository.listAll()).satisfiesExactlyInAnyOrder(
            {
                assertThat(it.id).isNotNull()
                assertThat(it.items).satisfiesExactlyInAnyOrder({ item -> assertThat(item).isEqualTo(UUID.fromString("63e01f7a-aded-4353-acce-095cd8ed8f18")) })
                assertThat(it.userId).isEqualTo(UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84"))
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