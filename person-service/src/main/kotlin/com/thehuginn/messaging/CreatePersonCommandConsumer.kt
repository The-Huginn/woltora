package com.thehuginn.messaging

import com.thehuginn.messaging.dto.command.CreatePersonCommandMessage
import com.thehuginn.repository.MessageRepository.Message.CREATE_PERSON
import com.thehuginn.service.PersonService
import com.thehuginn.service.command.mapper.CreatePersonCommandMapper
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.reactive.messaging.Incoming
import java.util.logging.Logger

@ApplicationScoped
class CreatePersonCommandConsumer (
    private val personService: PersonService,
    private val createPersonCommandMapper: CreatePersonCommandMapper
) {

    private val logger = Logger.getLogger(CreatePersonCommandConsumer::class.java.toString())

    @Incoming(CREATE_PERSON)
    @Transactional
    fun process(message: CreatePersonCommandMessage) =
        Uni.createFrom().item(message)
            .onItem().transformToUni { msg ->
                Uni.createFrom().item(personService.persist(createPersonCommandMapper.mapFrom(msg)))
            }
            .onFailure().invoke { throwable -> logger.info("Error during message processing: $throwable") }
            .replaceWithVoid()

}