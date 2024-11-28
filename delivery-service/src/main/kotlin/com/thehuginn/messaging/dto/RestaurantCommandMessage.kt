package com.thehuginn.messaging.dto

import java.math.BigDecimal
import java.util.UUID

data class RestaurantCommandMessage(
    val id: UUID,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)
