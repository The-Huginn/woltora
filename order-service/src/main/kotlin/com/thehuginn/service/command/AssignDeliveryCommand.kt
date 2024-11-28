package com.thehuginn.service.command

import java.util.UUID

data class AssignDeliveryCommand(
    val orderId: UUID,
    val driverId: UUID
)
