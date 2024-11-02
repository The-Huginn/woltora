package com.thehuginn.messaging

import com.thehuginn.domain.Order
import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.messaging.MessageRepository.Message.CREATE_ORDER
import com.thehuginn.messaging.dto.CreateOrderCommandMessage
import com.thehuginn.repositories.OrderRepository
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class CreateOrderCommandConsumer(
    private val orderRepository: OrderRepository
) {

    @Incoming(CREATE_ORDER)
    fun process(message: CreateOrderCommandMessage) {
        val order = Order(
            userId = message.userId,
            restaurantId = message.restaurantId,
            status = CREATED
        )
        order.items.addAll(message.items)
        orderRepository.save(order)
    }
}