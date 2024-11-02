package com.thehuginn.messaging

import com.thehuginn.domain.Order
import com.thehuginn.enums.OrderStatus.IN_DELIVERY
import com.thehuginn.exceptions.NotFoundException
import com.thehuginn.messaging.dto.AssignDeliveryCommandMessage
import com.thehuginn.repository.MessageRepository.Message.ASSIGN_DELIVERY
import com.thehuginn.repository.OrderRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class AssignDeliveryCommandConsumer(
    private val orderRepository: OrderRepository
) {

    @Transactional
    @Incoming(ASSIGN_DELIVERY)
    fun process(message: AssignDeliveryCommandMessage): Uni<Void> =
        Uni.createFrom().item(message)
        .map {
            orderRepository.findById(it.orderId) ?: throw NotFoundException(Order::class.java, it.orderId)
        }
        .invoke { it -> it.status = IN_DELIVERY }
        .invoke { it -> orderRepository.persistAndFlush(it) }
        .replaceWithVoid()
}