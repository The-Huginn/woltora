package com.thehuginn.messaging

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision.MS
import com.thehuginn.measurement.OrderLocation
import com.thehuginn.messaging.dto.UpdateOrderLocationCommandMessage
import com.thehuginn.repository.MessageRepository.Message.UPDATE_LOCATION
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logmanager.Logger

@ApplicationScoped
class UpdateOrderLocationCommandConsumer(
    val influxDBClient: InfluxDBClient
) {

    private val logger = Logger.getLogger(UpdateOrderLocationCommandConsumer::class.java.toString())

    @Incoming(UPDATE_LOCATION)
    @Transactional
    fun process(message: UpdateOrderLocationCommandMessage): Uni<Void> =
        Uni.createFrom().item(message)
            .invoke { msg -> logger.info("Updating location for order ${msg.orderId} to location ${msg.location}") }
            .map { OrderLocation(it.location, it.orderId.toString()) }
            .invoke { orderLocation -> influxDBClient.writeApiBlocking.writeMeasurement(MS, orderLocation) }
            .onFailure().invoke { throwable -> logger.info("Error during message processing $throwable") }
            .replaceWithVoid()
}