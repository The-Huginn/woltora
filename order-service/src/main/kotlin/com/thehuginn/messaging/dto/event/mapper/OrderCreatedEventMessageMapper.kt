package com.thehuginn.messaging.dto.event.mapper

import com.thehuginn.messaging.dto.event.OrderCreatedEventMessage
import com.thehuginn.service.result.OrderResult
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface OrderCreatedEventMessageMapper {

    @Mapping(target = "orderId", source = "result.id")
    fun mapFrom(result: OrderResult): OrderCreatedEventMessage

}