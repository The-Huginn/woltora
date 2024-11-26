package com.thehuginn.service.result.mapper

import com.thehuginn.domain.Address
import com.thehuginn.service.result.AddressResult
import org.mapstruct.Mapper

@Mapper
interface AddressResultMapper {

    fun mapFrom(address: Address) : AddressResult

}