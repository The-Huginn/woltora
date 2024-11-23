package com.thehuginn.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
data class Restaurant(
    val name: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
) {
    @Id
    val id: UUID = randomUUID()

    constructor() : this("", ZERO, ZERO)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Restaurant

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
