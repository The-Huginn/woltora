package com.thehuginn.repository

import com.thehuginn.domain.Person
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class PersonRepository : PanacheRepositoryBase<Person, UUID> {

    fun getById(id: UUID) = findById(id) ?: throw NoSuchElementException()

    fun getByName(firstName: String?, lastName: String?): List<Person> {
        if (firstName == null && lastName == null) {
            return emptyList()
        }

        val queryString = StringBuilder("1=1")
        val params = mutableListOf<Any>()

        if (firstName != null) {
            queryString.append(" and firstName = ?1")
            params.add(firstName)
        }

        if (lastName != null) {
            queryString.append(" and lastName = ?2")
            params.add(lastName)
        }

        return find(queryString.toString(), *params.toTypedArray()).list()
    }

}