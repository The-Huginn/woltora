package com.thehuginn.scheduling

import com.thehuginn.integration.OrderServiceMicroservice
import com.thehuginn.messaging.dto.UpdateOrderLocationCommandMessage
import com.thehuginn.repository.MessageRepository.Message.UPDATE_LOCATION
import io.quarkus.scheduler.Scheduled
import jakarta.enterprise.context.ApplicationScoped
import java.lang.Math.random
import java.util.UUID
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.logging.Logger.getLogger

@ApplicationScoped
class UpdateLocationMockJob(
    @RestClient val orderServiceMicroservice: OrderServiceMicroservice,
    @Channel(UPDATE_LOCATION) val emitter: Emitter<UpdateOrderLocationCommandMessage>
) {

    private val logger = getLogger(UpdateLocationMockJob::class.java)
    private val userId = UUID.fromString("0111b250-1c15-44ae-8149-6eef0867ed84")
    
    @Scheduled(every = "10s")
    fun updateOrdersLocation() {
        try {
            orderServiceMicroservice.getOrdersByUserId(userId)
                .apply {
                    if (this.isNotEmpty()) {
                        logger.info("Updating location for orders for user $userId")
                    }
                }
                .map { UpdateOrderLocationCommandMessage(it.id, "Location: ${random()}") }
                .forEach { emitter.send(it) }
        } catch (_: Exception) {
            logger.info("Waiting for the user $userId to create order")
        }
    }
}