package com.thehuginn.messaging.dto

import java.math.BigDecimal
import java.util.UUID

data class CreateFoodCommandMessage(
    val restaurantId: UUID,
    val name: String,
    val description: String? = null,
    val price: BigDecimal,
    val available: Boolean = true
)