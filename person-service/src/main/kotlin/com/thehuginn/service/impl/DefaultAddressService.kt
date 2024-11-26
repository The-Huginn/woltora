package com.thehuginn.service.impl

import com.thehuginn.repository.AddressRepository
import com.thehuginn.service.AddressService
import com.thehuginn.service.result.AddressResult
import com.thehuginn.service.result.mapper.AddressResultMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.util.UUID

@ApplicationScoped
class DefaultAddressService(
    private val addressRepository : AddressRepository,
    private val addressResultMapper: AddressResultMapper
) : AddressService {

    @Transactional
    override fun getAddress(id: UUID) = addressResultMapper.mapFrom(addressRepository.getById(id))

    @Transactional
    override fun getAddressByUserId(userId: UUID): List<AddressResult> {
        return addressRepository.getByUserId(userId)
            .map { addressResultMapper.mapFrom(it) }
    }

}