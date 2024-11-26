package com.thehuginn.controller

import com.thehuginn.controller.dto.response.PersonResponse
import com.thehuginn.controller.dto.response.mapper.PersonResponseMapper
import com.thehuginn.service.PersonService
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.jboss.resteasy.reactive.RestPath
import org.jboss.resteasy.reactive.RestQuery
import java.util.UUID

@Path("/person")
class PersonResource (
    private val personService: PersonService,
    private val personResponseMapper: PersonResponseMapper
) {

    @GET
    @Path("/{id}")
    fun getPerson(@RestPath id: UUID) = personResponseMapper.mapFrom(personService.getPerson(id))

    @GET
    fun getPersonByName(
        @RestQuery firstName: String?,
        @RestQuery lastName: String?
    ): List<PersonResponse> {
        val personResults = personService.getPersonByName(firstName, lastName)
        return personResults.map { personResponseMapper.mapFrom(it) }
    }

}