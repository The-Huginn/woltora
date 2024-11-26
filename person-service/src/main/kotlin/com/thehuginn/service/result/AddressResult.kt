package com.thehuginn.service.result

import java.math.BigDecimal
import java.util.UUID

data class AddressResult (
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