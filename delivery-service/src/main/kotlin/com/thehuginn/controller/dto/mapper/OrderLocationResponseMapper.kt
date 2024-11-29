package com.thehuginn.controller.dto.mapper

import com.thehuginn.controller.dto.OrderLocationResponse
import com.thehuginn.service.result.OrderLocationResult
import org.mapstruct.Mapper

@Mapper
interface OrderLocationResponseMapper {

    fun mapFrom(result: OrderLocationResult): OrderLocationResponse

}