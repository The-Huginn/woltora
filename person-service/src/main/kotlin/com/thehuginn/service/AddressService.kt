package com.thehuginn.service

import com.thehuginn.service.result.AddressResult
import java.util.UUID

interface AddressService {

    fun getAddress(id: UUID): AddressResult

    fun getAddressByUserId(userId: UUID) : List<AddressResult>

}