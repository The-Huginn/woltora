package com.thehuginn.service.impl

import com.influxdb.client.QueryApi
import com.thehuginn.exception.NotFoundException
import com.thehuginn.measurement.OrderLocation
import com.thehuginn.service.OrderLocationService
import com.thehuginn.service.result.mapper.OrderLocationResultMapper
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class DefaultOrderLocationService(
    val queryApi: QueryApi,
    val orderLocationResultMapper: OrderLocationResultMapper
) : OrderLocationService {

    companion object {
        const val INFLUX_QUERY =
            """from(bucket: "quarkus")
            |> range(start: 0)
            |> filter(fn: (r) => r._measurement == "order_location" and r.orderId == "%s")
            |> last()"""
    }

    override fun getOrderLocation(orderId: UUID) =
        queryApi.query(INFLUX_QUERY.format(orderId), OrderLocation::class.java)
            .asSequence()
            .find { it != null }
            ?.let { orderLocationResultMapper.mapFrom(it) }
            ?: throw NotFoundException(OrderLocation::class.java, null)

}