package com.thehuginn.messaging.dto

import java.util.UUID

data class UpdateOrderLocationCommandMessage(
    val orderId: UUID,
    val location: String
)
