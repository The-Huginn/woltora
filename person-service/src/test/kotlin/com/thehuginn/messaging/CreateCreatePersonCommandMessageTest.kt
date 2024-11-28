package com.thehuginn.messaging

import com.thehuginn.enums.PersonType.CUSTOMER
import com.thehuginn.messaging.dto.command.CreatePersonCommandMessage
import com.thehuginn.repository.MessageRepository.Message.CREATE_PERSON
import com.thehuginn.repository.PersonRepository
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

@QuarkusTest
class CreateCreatePersonCommandMessageTest {

    @Inject
    lateinit var personRepository: PersonRepository

    @Inject
    @Channel(CREATE_PERSON)
    lateinit var emitter: Emitter<CreatePersonCommandMessage>

    @Test
    fun testCreatePersonMessage() {
        emitter.send(
            CreatePersonCommandMessage(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@gmail.com",
                type = CUSTOMER
            )
        )

        sleep(1000)

        assertThat(personRepository.listAll()).satisfiesExactlyInAnyOrder({
            assertThat(it.id).isNotNull()
            assertThat(it.firstName).isEqualTo("John")
            assertThat(it.lastName).isEqualTo("Doe")
            assertThat(it.email).isEqualTo("john.doe@gmail.com")
            assertThat(it.type).isEqualTo(CUSTOMER)
        })
    }

}