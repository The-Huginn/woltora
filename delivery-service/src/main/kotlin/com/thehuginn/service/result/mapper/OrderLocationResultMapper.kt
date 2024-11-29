package com.thehuginn.service.result.mapper

import com.thehuginn.measurement.OrderLocation
import com.thehuginn.service.result.OrderLocationResult
import org.mapstruct.Mapper

@Mapper
interface OrderLocationResultMapper {

    fun mapFrom(order: OrderLocation): OrderLocationResult

}