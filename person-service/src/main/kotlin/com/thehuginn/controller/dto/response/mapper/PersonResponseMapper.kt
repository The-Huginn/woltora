package com.thehuginn.controller.dto.response.mapper

import com.thehuginn.controller.dto.response.PersonResponse
import com.thehuginn.service.result.PersonResult
import org.mapstruct.Mapper

@Mapper
interface PersonResponseMapper {

    fun mapFrom(result: PersonResult): PersonResponse

}