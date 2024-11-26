package com.thehuginn.service

import com.thehuginn.service.command.OrderCommand
import com.thehuginn.service.result.OrderResult
import java.util.UUID

interface OrderService {

    fun persist(command: OrderCommand): OrderResult

    fun getOrder(id: UUID): OrderResult

    fun getOrdersByUserId(userId: UUID): List<OrderResult>

}