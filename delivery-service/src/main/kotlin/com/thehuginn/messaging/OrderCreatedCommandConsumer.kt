package com.thehuginn.messaging

import com.thehuginn.messaging.dto.AssignDeliveryEventMessage
import com.thehuginn.messaging.dto.OrderCreatedCommandMessage
import com.thehuginn.repository.MessageRepository.Message.ASSIGN_DELIVERY
import com.thehuginn.repository.MessageRepository.Message.ORDER_CREATED
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.time.Duration
import java.util.UUID
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import org.slf4j.LoggerFactory.getLogger

@ApplicationScoped
class OrderCreatedCommandConsumer {

    private val logger = getLogger(OrderCreatedCommandConsumer::class.java.toString())
    private val driverId = UUID.fromString("7272e01d-3f11-473c-9462-5f62edea852c")

    @Incoming(ORDER_CREATED)
    @Outgoing(ASSIGN_DELIVERY)
    fun process(message: OrderCreatedCommandMessage) = Multi.createFrom().item(message)
        .onItem().transform { it.orderId }
        .invoke { it -> logger.info("Received order {} and assigning to driver {}", it, driverId) }
        .onItem().transformToUniAndMerge {
            Uni.createFrom().item(it)
                // Simulate driver assigning himself the order
                .onItem().delayIt().by(Duration.ofSeconds(5))
                .onItem().transform { AssignDeliveryEventMessage(it, driverId) }
        }
}