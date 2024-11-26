package com.thehuginn.service.command

import com.thehuginn.enums.OrderStatus
import java.util.UUID

data class OrderCommand(
    val userId: UUID,
    val status: OrderStatus,
    val restaurantId: UUID,
    val items: List<UUID>
)
