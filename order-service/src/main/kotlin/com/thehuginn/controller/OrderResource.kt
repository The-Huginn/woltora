package com.thehuginn.controller

import com.thehuginn.controller.dto.response.mapper.OrderResponseMapper
import com.thehuginn.service.OrderService
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import java.util.UUID

@Path("/order")
class OrderResource(
    private val orderService: OrderService,
    private val orderResponseMapper: OrderResponseMapper
) {

    @GET
    @Path("/{id}")
    fun getOrder(id: UUID) = orderResponseMapper.mapFrom(orderService.getOrder(id))
}