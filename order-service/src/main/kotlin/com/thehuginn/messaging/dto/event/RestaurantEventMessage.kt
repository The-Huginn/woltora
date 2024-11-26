package com.thehuginn.messaging.dto.event

import java.math.BigDecimal
import java.util.UUID

data class RestaurantEventMessage(
    val id: UUID,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)
