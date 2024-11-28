package com.thehuginn.service.impl

import com.thehuginn.domain.mapper.AddressMapper
import com.thehuginn.repository.AddressRepository
import com.thehuginn.repository.PersonRepository
import com.thehuginn.service.AddressService
import com.thehuginn.service.command.CreateAddressCommand
import com.thehuginn.service.result.AddressResult
import com.thehuginn.service.result.mapper.AddressResultMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.util.UUID

@ApplicationScoped
class DefaultAddressService(
    private val personRepository: PersonRepository,
    private val addressRepository : AddressRepository,
    private val addressResultMapper: AddressResultMapper,
    private val addressMapper: AddressMapper
) : AddressService {

    @Transactional
    override fun getAddress(id: UUID) = addressResultMapper.mapFrom(addressRepository.getById(id))

    @Transactional
    override fun getAddressByUserId(userId: UUID): List<AddressResult> {
        return addressRepository.getByUserId(userId)
            .map { addressResultMapper.mapFrom(it) }
    }

    @Transactional
    override fun persist(command: CreateAddressCommand): AddressResult {
        val address = addressMapper.mapFrom(command)

        val foundPerson = personRepository.getById(command.personId)

        address.person = foundPerson

        addressRepository.persist(address)
        return addressResultMapper.mapFrom(address)
    }

}