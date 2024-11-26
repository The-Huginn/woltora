package com.thehuginn.service.result

import java.math.BigDecimal
import java.util.UUID

data class RestaurantResult(
    val id: UUID,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)
