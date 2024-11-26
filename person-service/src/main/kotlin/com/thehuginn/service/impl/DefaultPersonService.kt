package com.thehuginn.service.impl

import com.thehuginn.repository.PersonRepository
import com.thehuginn.service.PersonService
import com.thehuginn.service.result.PersonResult
import com.thehuginn.service.result.mapper.PersonResultMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.util.UUID

@ApplicationScoped
class DefaultPersonService(
    private val personRepository: PersonRepository,
    private val personResultMapper: PersonResultMapper
) : PersonService {

    @Transactional
    override fun getPerson(id: UUID) = personResultMapper.mapFrom(personRepository.getById(id))

    @Transactional
    override fun getPersonByName(firstName: String?, lastName: String?): List<PersonResult> {
        return personRepository.getByName(firstName, lastName)
            .map { personResultMapper.mapFrom(it) }
    }

}