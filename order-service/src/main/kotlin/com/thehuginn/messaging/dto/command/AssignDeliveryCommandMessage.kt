package com.thehuginn.messaging.dto.command

import java.util.UUID

data class AssignDeliveryCommandMessage(
    val orderId: UUID
)