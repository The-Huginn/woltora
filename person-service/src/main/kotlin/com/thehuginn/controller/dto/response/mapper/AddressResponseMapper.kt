package com.thehuginn.controller.dto.response.mapper

import com.thehuginn.controller.dto.response.AddressResponse
import com.thehuginn.service.result.AddressResult
import org.mapstruct.Mapper

@Mapper
interface AddressResponseMapper {

    fun mapFrom(result: AddressResult) : AddressResponse

}