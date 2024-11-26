package com.thehuginn.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction.CASCADE
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.util.UUID
import java.util.UUID.randomUUID

@Entity
data class Address(

    val country: String,

    val city: String,

    val street: String,

    val number: String,

    val postalCode: String,

    val description: String,

    val latitude: BigDecimal,

    val longitude: BigDecimal
) {
    @Id
    val id: UUID = randomUUID()

    @ManyToOne
    @OnDelete(action = CASCADE)
    @JoinColumn(name = "person_id", nullable = true)
    var person: Person? = null

    constructor() : this("", "", "", "", "", "", ZERO, ZERO)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}