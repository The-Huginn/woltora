package com.thehuginn.messaging.dto.event

import java.util.UUID

data class OrderCreatedEventMessage(
    val orderId: UUID,
    val restaurant: RestaurantEventMessage,
)