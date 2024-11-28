package com.thehuginn.messaging

import com.thehuginn.domain.Person
import com.thehuginn.exception.NotFoundException
import com.thehuginn.messaging.dto.command.CreateAddressCommandMessage
import com.thehuginn.repository.MessageRepository.Message.CREATE_ADDRESS
import com.thehuginn.service.AddressService
import com.thehuginn.service.PersonService
import com.thehuginn.service.command.mapper.CreateAddressCommandMapper
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.util.logging.Logger

@ApplicationScoped
class CreateAddressCommandConsumer (
    private val personService: PersonService,
    private val addressService: AddressService,
    private val createAddressCommandMapper: CreateAddressCommandMapper
) {

    private val logger = Logger.getLogger(CreateAddressCommandConsumer::class.java.toString())

    @Incoming(CREATE_ADDRESS)
    @Transactional
    fun process(message: CreateAddressCommandMessage): Uni<Void> =
        Uni.createFrom().item(message)
            .onItem().transformToUni { msg ->
                Uni.createFrom().item { personService.getPerson(msg.personId) }
                    .onItem().ifNull().failWith(NotFoundException(Person::class.java, msg.personId))
                    .replaceWith(msg)
            }
            .onItem().transformToUni { msg ->
                Uni.createFrom().item(addressService.persist(createAddressCommandMapper.mapFrom(msg)))
            }
            .onFailure().invoke { throwable -> logger.info("Error during message processing: $throwable") }
            .replaceWithVoid()

}