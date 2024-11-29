package com.thehuginn.repository

import com.thehuginn.domain.Person
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Parameters
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
        val parameters = Parameters()

        firstName?.let {
            queryString.append(" and firstName = :firstName")
            parameters.and("firstName", it)
        }
        lastName?.let {
            queryString.append(" and lastName = :lastName")
            parameters.and("lastName", it)
        }

        return find(queryString.toString(), parameters).list()
    }

}