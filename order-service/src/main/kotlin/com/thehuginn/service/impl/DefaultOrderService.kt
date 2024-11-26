package com.thehuginn.service.impl

import com.thehuginn.domain.Order
import com.thehuginn.domain.Restaurant
import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.exception.NotFoundException
import com.thehuginn.repository.OrderRepository
import com.thehuginn.repository.RestaurantRepository
import com.thehuginn.service.OrderService
import com.thehuginn.service.command.OrderCommand
import com.thehuginn.service.result.OrderResult
import com.thehuginn.service.result.mapper.OrderResultMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.util.UUID

@ApplicationScoped
class DefaultOrderService(
    private val orderRepository: OrderRepository,
    private val restaurantRepository: RestaurantRepository,
    private val orderResultMapper: OrderResultMapper
) : OrderService {

    @Transactional
    override fun getOrder(id: UUID) = orderResultMapper.mapFrom(
        orderRepository.getById(id)
    )

    @Transactional
    override fun persist(command: OrderCommand): OrderResult {
        val order = Order(
            status = CREATED,
            userId = command.userId,
            restaurant = restaurantRepository.findById(command.restaurantId) ?: throw NotFoundException(Restaurant::class.java, command.restaurantId)
        )
        order.items.addAll(command.items)

        orderRepository.persist(order)
        return orderResultMapper.mapFrom(order)
    }

    @Transactional
    override fun getOrdersByUserId(userId: UUID) = orderRepository.list(Order::userId.name, userId)
        .map { orderResultMapper.mapFrom(it) }
}