package com.thehuginn.service

import com.thehuginn.service.command.CreatePersonCommand
import com.thehuginn.service.result.PersonResult
import java.util.UUID

interface PersonService {

    fun getPerson(id: UUID): PersonResult

    fun getPersonByName(firstName: String?, lastName: String?): List<PersonResult>

    fun persist(command: CreatePersonCommand): PersonResult

}