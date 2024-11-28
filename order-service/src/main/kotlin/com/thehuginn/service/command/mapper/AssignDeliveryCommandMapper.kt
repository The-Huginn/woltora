package com.thehuginn.service.command.mapper

import com.thehuginn.messaging.dto.command.AssignDeliveryCommandMessage
import com.thehuginn.service.command.AssignDeliveryCommand
import org.mapstruct.Mapper

@Mapper
interface AssignDeliveryCommandMapper {

    fun mapFrom(message: AssignDeliveryCommandMessage): AssignDeliveryCommand

}