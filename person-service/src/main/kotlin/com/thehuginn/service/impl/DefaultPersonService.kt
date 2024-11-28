package com.thehuginn.service.impl

import com.thehuginn.domain.Person
import com.thehuginn.domain.mapper.PersonMapper
import com.thehuginn.repository.PersonRepository
import com.thehuginn.service.PersonService
import com.thehuginn.service.command.CreatePersonCommand
import com.thehuginn.service.result.PersonResult
import com.thehuginn.service.result.mapper.PersonResultMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.util.UUID

@ApplicationScoped
class DefaultPersonService(
    private val personRepository: PersonRepository,
    private val personResultMapper: PersonResultMapper,
    private val personMapper: PersonMapper
) : PersonService {

    @Transactional
    override fun getPerson(id: UUID) = personResultMapper.mapFrom(personRepository.getById(id))

    @Transactional
    override fun getPersonByName(firstName: String?, lastName: String?): List<PersonResult> {
        return personRepository.getByName(firstName, lastName)
            .map { personResultMapper.mapFrom(it) }
    }

    @Transactional
    override fun persist(command: CreatePersonCommand): PersonResult {
        val person = personMapper.mapFrom(command)

        personRepository.persist(person)
        return personResultMapper.mapFrom(person)
    }

}