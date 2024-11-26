package com.thehuginn.controller.dto.response

import java.math.BigDecimal
import java.util.UUID

data class AddressResponse(
    val id: UUID,
    val country: String,
    val city: String,
    val street: String,
    val number: String,
    val postalCode: String,
    val description: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)