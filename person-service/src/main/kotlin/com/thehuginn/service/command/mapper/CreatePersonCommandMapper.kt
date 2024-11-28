package com.thehuginn.service.command.mapper

import com.thehuginn.messaging.dto.command.CreatePersonCommandMessage
import com.thehuginn.service.command.CreatePersonCommand
import org.mapstruct.Mapper

@Mapper
interface CreatePersonCommandMapper {

    fun mapFrom(message: CreatePersonCommandMessage): CreatePersonCommand

}