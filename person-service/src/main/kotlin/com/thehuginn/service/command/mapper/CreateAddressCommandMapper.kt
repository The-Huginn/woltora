package com.thehuginn.service.command.mapper

import com.thehuginn.messaging.dto.command.CreateAddressCommandMessage
import com.thehuginn.service.command.CreateAddressCommand
import org.mapstruct.Mapper

@Mapper
interface CreateAddressCommandMapper {

    fun mapFrom(message: CreateAddressCommandMessage): CreateAddressCommand

}