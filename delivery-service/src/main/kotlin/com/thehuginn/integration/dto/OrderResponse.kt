package com.thehuginn.integration.dto

import com.thehuginn.enums.OrderStatus
import java.time.Instant
import java.util.UUID

data class OrderResponse(
    val id: UUID,
    val status: OrderStatus,
    val userId: UUID,
    val restaurantId: UUID,
    val items: List<UUID>,
    val creationTimestamp: Instant
)
