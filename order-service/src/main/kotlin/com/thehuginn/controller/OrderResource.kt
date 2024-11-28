package com.thehuginn.controller

import com.thehuginn.controller.dto.response.mapper.OrderResponseMapper
import com.thehuginn.repository.OrderRepository
import com.thehuginn.service.OrderService
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import java.util.UUID
import org.jboss.resteasy.reactive.RestQuery

@Path("/order")
@ApplicationScoped
class OrderResource(
    private val orderService: OrderService,
    private val orderResponseMapper: OrderResponseMapper,
    private val orderRepository: OrderRepository
) {

    @GET
    @Path("/{id}")
    fun getOrder(id: UUID) = orderService.getOrder(id)
        .map { orderResponseMapper.mapFrom(it) }

    @GET
    fun gerOrdersByUserId(@RestQuery userId: UUID) = orderService.getOrdersByUserId(userId)
        .map { it.map { order -> orderResponseMapper.mapFrom(order) } }

    @GET
    @Path("/all")
    @WithTransaction
    fun getAllOrders() = orderRepository.listAll()
}