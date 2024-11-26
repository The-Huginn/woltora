package com.thehuginn.messaging.dto.event.mapper

import com.thehuginn.messaging.dto.event.OrderCreatedEventMessage
import com.thehuginn.service.result.OrderResult
import org.mapstruct.Mapper

@Mapper
interface OrderCreatedEventMessageMapper {

    fun mapFrom(result: OrderResult): OrderCreatedEventMessage

}