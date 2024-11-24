package com.thehuginn.service

import com.thehuginn.service.result.OrderResult
import java.util.UUID

interface OrderService {

    fun getOrder(id: UUID): OrderResult

}