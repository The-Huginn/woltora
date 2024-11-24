package com.thehuginn.controller.dto.response.mapper

import com.thehuginn.controller.dto.response.OrderResponse
import com.thehuginn.service.result.OrderResult
import org.mapstruct.Mapper

@Mapper
interface OrderResponseMapper {

    fun mapFrom(entity: OrderResult): OrderResponse

}