package com.thehuginn.domain

import com.thehuginn.enums.PersonType
import com.thehuginn.enums.PersonType.CUSTOMER
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.CascadeType
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.EnumType.STRING
import java.util.UUID
import java.util.UUID.randomUUID
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes.NAMED_ENUM

@Entity
data class Person(

    @Enumerated(STRING)
    @JdbcTypeCode(NAMED_ENUM)
    val role: PersonType,

    var firstName: String,

    var lastName: String,

    var email: String
) {
    @Id
    val id: UUID = randomUUID()

    @OneToMany(mappedBy = "person", cascade = [ALL], orphanRemoval = true)
    val addresses: MutableList<Address> = mutableListOf()

    constructor() : this(CUSTOMER, "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}