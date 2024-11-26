package com.thehuginn.service.result.mapper

import com.thehuginn.domain.Person
import com.thehuginn.service.result.PersonResult
import org.mapstruct.Mapper

@Mapper
interface PersonResultMapper {

    fun mapFrom(person: Person) : PersonResult

}