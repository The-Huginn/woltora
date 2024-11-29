package com.thehuginn.controller.dto

import java.util.UUID

data class OrderLocationResponse(
    val location: String,
    val orderId: UUID
)
