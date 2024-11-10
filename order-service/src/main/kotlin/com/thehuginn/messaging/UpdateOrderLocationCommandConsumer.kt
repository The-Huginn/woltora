package com.thehuginn.messaging

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision.MS
import com.thehuginn.domain.Order
import com.thehuginn.exceptions.NotFoundException
import com.thehuginn.measurement.OrderLocation
import com.thehuginn.messaging.dto.UpdateOrderLocationCommandMessage
import com.thehuginn.repository.MessageRepository.Message.UPDATE_LOCATION
import com.thehuginn.repository.OrderRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.time.Instant.now
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logmanager.Logger

@ApplicationScoped
class UpdateOrderLocationCommandConsumer(
    val influxDBClient: InfluxDBClient,
    val orderRepository: OrderRepository
) {

    private val logger = Logger.getLogger(UpdateOrderLocationCommandConsumer::class.java.toString())

    @Incoming(UPDATE_LOCATION)
    @Transactional
    fun process(message: UpdateOrderLocationCommandMessage): Uni<Void> =
        Uni.createFrom().item(message)
            .onItem().transformToUni { msg ->
                logger.info("Processing message: $msg")
                Uni.createFrom().item { orderRepository.findById(msg.orderId) }
                    .onItem().ifNull().failWith(NotFoundException(Order::class.java, msg.orderId))
                    .map {
                        OrderLocation(
                            location = msg.location,
                            orderId = msg.orderId,
                            timestamp = now()
                        )
                    }
            }
            .invoke { orderLocation ->
                influxDBClient.makeWriteApi().use { it.writeMeasurement(MS, orderLocation) }
            }
            .onFailure().invoke { throwable ->
                logger.info("Error during message processing $throwable")
            }
            .replaceWithVoid()
}