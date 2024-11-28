package com.thehuginn.domain.mapper

import com.thehuginn.domain.Address
import com.thehuginn.service.command.CreateAddressCommand
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface AddressMapper {

    @Mapping(target = "person", ignore = true)
    fun mapFrom(command: CreateAddressCommand): Address

}