package com.thehuginn.controller.dto.response.mapper

import com.thehuginn.controller.dto.response.OrderResponse
import com.thehuginn.service.result.OrderResult
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface OrderResponseMapper {

    @Mapping(target = "restaurantId", source = "result.restaurant.id")
    fun mapFrom(result: OrderResult): OrderResponse

}