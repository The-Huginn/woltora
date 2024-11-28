package com.thehuginn.service

import com.thehuginn.service.command.AssignDeliveryCommand
import com.thehuginn.service.command.OrderCommand
import com.thehuginn.service.result.OrderResult
import io.smallrye.mutiny.Uni
import java.util.UUID

interface OrderService {

    fun persist(command: OrderCommand): Uni<OrderResult>

    fun getOrder(id: UUID): Uni<OrderResult>

    fun getOrdersByUserId(userId: UUID): Uni<List<OrderResult>>

    fun assignOrder(command: AssignDeliveryCommand): Uni<Void>

}