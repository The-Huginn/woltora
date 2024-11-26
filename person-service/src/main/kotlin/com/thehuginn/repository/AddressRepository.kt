package com.thehuginn.repository

import com.thehuginn.domain.Address
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class AddressRepository : PanacheRepositoryBase<Address, UUID> {

    fun getById(id: UUID) = findById(id) ?: throw NoSuchElementException()

    fun getByUserId(userId: UUID): List<Address> {
        return find("person.id", userId).list()
    }

}