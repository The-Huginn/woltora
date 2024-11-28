package com.thehuginn.messaging

import com.thehuginn.messaging.dto.command.AssignDeliveryCommandMessage
import com.thehuginn.repository.MessageRepository.Message.ASSIGN_DELIVERY
import com.thehuginn.service.OrderService
import com.thehuginn.service.command.mapper.AssignDeliveryCommandMapper
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logging.Logger.getLogger

@ApplicationScoped
class AssignDeliveryCommandConsumer(
    private val orderService: OrderService,
    private val assignDeliveryCommandMapper: AssignDeliveryCommandMapper
) {
    private val logger = getLogger(AssignDeliveryCommandConsumer::class.java.toString())

    @Incoming(ASSIGN_DELIVERY)
    fun process(message: AssignDeliveryCommandMessage): Uni<Void> =
        Uni.createFrom().item(message)
            .onItem().invoke { msg -> logger.info("Assigning ${msg.orderId} to driver ${msg.driverId}") }
            .map { assignDeliveryCommandMapper.mapFrom(it) }
            .call { it -> orderService.assignOrder(it) }
            .replaceWithVoid()
            .onFailure().invoke { throwable -> logger.info("Error during message processing $throwable")}
}