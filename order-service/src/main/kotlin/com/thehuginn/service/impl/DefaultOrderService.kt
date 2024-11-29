package com.thehuginn.service.impl

import com.thehuginn.domain.Order
import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.enums.OrderStatus.IN_DELIVERY
import com.thehuginn.repository.OrderRepository
import com.thehuginn.repository.RestaurantRepository
import com.thehuginn.service.OrderService
import com.thehuginn.service.command.AssignDeliveryCommand
import com.thehuginn.service.command.OrderCommand
import com.thehuginn.service.result.OrderResult
import com.thehuginn.service.result.mapper.OrderResultMapper
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class DefaultOrderService(
    private val orderRepository: OrderRepository,
    private val restaurantRepository: RestaurantRepository,
    private val orderResultMapper: OrderResultMapper
) : OrderService {

    @WithTransaction
    override fun getOrder(id: UUID) =
        orderRepository.getById(id).map { orderResultMapper.mapFrom(it) }

    @WithTransaction
    override fun persist(command: OrderCommand): Uni<OrderResult> {
        return restaurantRepository.findById(command.restaurantId)
            .map {
                Order(
                    status = CREATED,
                    userId = command.userId,
                    addressId = command.addressId,
                    restaurant = it
                )
            }
            .invoke { it -> it.items.addAll(command.items) }
            .call { it -> orderRepository.persist(it) }
            .map { orderResultMapper.mapFrom(it) }
    }

    @WithTransaction
    override fun getAll() = orderRepository.listAll()
        .map { it.map { order -> orderResultMapper.mapFrom(order) } }

    @WithTransaction
    override fun getOrdersByUserId(userId: UUID) = orderRepository.list(Order::userId.name, userId)
        .map { it.map { order -> orderResultMapper.mapFrom(order) } }

    @WithTransaction
    override fun assignOrder(command: AssignDeliveryCommand) = orderRepository.getById(command.orderId)
        .invoke { it -> it.status = IN_DELIVERY }
        .call { it -> orderRepository.persist(it) }
        .replaceWithVoid()
}