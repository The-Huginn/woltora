package com.thehuginn.service.command.mapper

import com.thehuginn.messaging.dto.command.CreateOrderCommandMessage
import com.thehuginn.service.command.OrderCommand
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface OrderCommandMapper {

    @Mapping(target = "status", constant = "CREATED")
    fun mapFrom(message: CreateOrderCommandMessage): OrderCommand

}