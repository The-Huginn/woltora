package com.thehuginn.messaging.dto.command

import java.util.UUID

data class CreateOrderCommandMessage(
    val userId: UUID,
    val restaurantId: UUID,
    val items: List<UUID>
)
