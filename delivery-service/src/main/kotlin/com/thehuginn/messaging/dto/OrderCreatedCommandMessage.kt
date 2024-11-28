package com.thehuginn.messaging.dto

import java.util.UUID

data class OrderCreatedCommandMessage(
   val orderId: UUID,
    val restaurant: RestaurantCommandMessage
)
