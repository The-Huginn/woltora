package com.thehuginn.service.result

import com.thehuginn.enums.OrderStatus
import java.time.Instant
import java.util.UUID

data class OrderResult(
    val id: UUID,
    val status: OrderStatus,
    val userId: UUID,
    val restaurant: RestaurantResult,
    val items: List<UUID>,
    val creationTimestamp: Instant
)
