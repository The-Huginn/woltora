package com.thehuginn.service

import com.thehuginn.service.result.OrderLocationResult
import java.util.UUID

interface OrderLocationService {

    fun getOrderLocation(orderId: UUID): OrderLocationResult

}