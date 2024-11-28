package com.thehuginn.messaging.dto

import java.util.UUID

data class AssignDeliveryEventMessage(
    val orderId: UUID,
    val driverId: UUID
)
