package com.thehuginn.controller

import com.thehuginn.controller.dto.mapper.OrderLocationResponseMapper
import com.thehuginn.service.OrderLocationService
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import java.util.UUID
import org.jboss.resteasy.reactive.RestQuery

@Path("/order-location")
class OrderLocationResource(
    val orderLocationService: OrderLocationService,
    val orderLocationResponseMapper: OrderLocationResponseMapper
) {

    @GET
    fun getOrderLocation(@RestQuery orderId: UUID) = orderLocationService.getOrderLocation(orderId)
        .let { orderLocationResponseMapper.mapFrom(it) }

}