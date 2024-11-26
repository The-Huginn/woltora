package com.thehuginn.messaging.dto.event

import java.util.UUID

data class OrderCreatedEventMessage(
    val id: UUID,
    val restaurant: RestaurantEventMessage,
)