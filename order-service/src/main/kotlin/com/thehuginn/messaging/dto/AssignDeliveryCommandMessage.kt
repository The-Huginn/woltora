package com.thehuginn.messaging.dto

import java.util.UUID

data class AssignDeliveryCommandMessage(
    val orderId: UUID
)