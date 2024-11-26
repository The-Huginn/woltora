package com.thehuginn.service.result.mapper

import com.thehuginn.domain.Order
import com.thehuginn.service.result.OrderResult
import org.mapstruct.Mapper

@Mapper
interface OrderResultMapper {

    fun mapFrom(order: Order): OrderResult

}