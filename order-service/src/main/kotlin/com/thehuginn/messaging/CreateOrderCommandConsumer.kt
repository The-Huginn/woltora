package com.thehuginn.messaging

import com.thehuginn.domain.Order
import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.messaging.dto.CreateOrderCommandMessage
import com.thehuginn.repository.MessageRepository.Message.CREATE_ORDER
import com.thehuginn.repository.OrderRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class CreateOrderCommandConsumer(
    private val orderRepository: OrderRepository
) {

    @Incoming(CREATE_ORDER)
    @Transactional
    fun process(message: CreateOrderCommandMessage): Uni<Void> =
        Uni.createFrom().item(message)
        .map {
            val order = Order(
                userId = it.userId,
                restaurantId = it.restaurantId,
                status = CREATED
            )
            order.items.addAll(message.items)

            order
        }
        .invoke { it -> orderRepository.persist(it) }
        .replaceWithVoid()
}