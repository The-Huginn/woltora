package com.thehuginn.domain.mapper

import com.thehuginn.domain.Person
import com.thehuginn.service.command.CreatePersonCommand
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface PersonMapper {

    @Mapping(target = "addresses", ignore = true)
    fun mapFrom(command: CreatePersonCommand): Person

}