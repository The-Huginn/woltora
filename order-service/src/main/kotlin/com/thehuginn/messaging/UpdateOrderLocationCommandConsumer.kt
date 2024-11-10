package com.thehuginn.messaging

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.domain.WritePrecision.MS
import com.thehuginn.measurement.OrderLocation
import com.thehuginn.messaging.dto.UpdateOrderLocationCommandMessage
import com.thehuginn.repository.MessageRepository.Message.UPDATE_LOCATION
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant.now
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class UpdateOrderLocationCommandConsumer(
    val influxDBClient: InfluxDBClient
) {

    @Incoming(UPDATE_LOCATION)
    fun process(message: UpdateOrderLocationCommandMessage): Uni<Void> = Uni.createFrom().item(message)
        .map {
            OrderLocation(
                location = it.location,
                orderId = it.orderId,
                timestamp = now()
            )
        }
        .invoke { it ->
            val writeApi = influxDBClient.makeWriteApi()
            writeApi.writeMeasurement(MS, it)
            writeApi.flush()
        }
        .replaceWithVoid()
}