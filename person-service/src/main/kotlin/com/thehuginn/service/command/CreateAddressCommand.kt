package com.thehuginn.service.command

import java.math.BigDecimal
import java.util.UUID

class CreateAddressCommand (
    val country: String,
    val city: String,
    val street: String,
    val number: String,
    val postalCode: String,
    val description: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val personId: UUID
)