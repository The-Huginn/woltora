package com.thehuginn.controller

import com.thehuginn.controller.dto.response.AddressResponse
import com.thehuginn.controller.dto.response.mapper.AddressResponseMapper
import com.thehuginn.service.AddressService
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.jboss.resteasy.reactive.RestPath
import org.jboss.resteasy.reactive.RestQuery
import java.util.UUID

@Path("/address")
class AddressResource(
    private val addressService: AddressService,
    private val addressResponseMapper: AddressResponseMapper
) {

    @GET
    @Path ("/{id}")
    fun getAddress(@RestPath id: UUID): AddressResponse {
        return addressResponseMapper.mapFrom(addressService.getAddress(id))
    }

    @GET
    fun getAddressesByUserId(@RestQuery userId: UUID): List<AddressResponse> {
        val addressResults = addressService.getAddressByUserId(userId)
        return addressResults.map { addressResponseMapper.mapFrom(it) }
    }

}