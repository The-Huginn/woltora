package com.thehuginn.service.impl

import com.thehuginn.domain.Order
import com.thehuginn.repository.OrderRepository
import com.thehuginn.service.OrderService
import com.thehuginn.service.result.mapper.OrderResultMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.util.UUID

@ApplicationScoped
class DefaultOrderService(
    private val orderRepository: OrderRepository,
    private val orderResultMapper: OrderResultMapper
) : OrderService {

    @Transactional
    override fun getOrder(id: UUID) = orderResultMapper.mapFrom(
        orderRepository.getById(id)
    )

    @Transactional
    override fun getOrdersByUserId(userId: UUID) = orderRepository.list(Order::userId.name, userId)
        .map { orderResultMapper.mapFrom(it) }
}