package com.thehuginn.messaging

import com.thehuginn.messaging.dto.command.CreateAddressCommandMessage
import com.thehuginn.repository.AddressRepository
import com.thehuginn.repository.MessageRepository.Message.CREATE_ADDRESS
import com.thehuginn.repository.PersonRepository
import com.thehuginn.sql.QuarkusTestWithSql
import com.thehuginn.sql.Sql
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.junit.jupiter.api.Test
import java.lang.Thread.*
import java.math.BigDecimal
import java.util.UUID

@QuarkusTest
@QuarkusTestWithSql
class CreateAddressCommandMessageTest {

    @Inject
    lateinit var addressRepository: AddressRepository

    @Inject
    lateinit var personRepository: PersonRepository

    @Inject
    @Channel(CREATE_ADDRESS)
    lateinit var emitter: Emitter<CreateAddressCommandMessage>

    @Test
    @Sql(["sql/simple-person.sql"])
    fun testCreateAddressMessage() {
        emitter.send(
            CreateAddressCommandMessage(
                country = "ExampleCountry",
                city = "ExampleCity",
                street = "ExampleStreet",
                number = "ExampleNumber",
                description = "Description",
                latitude = BigDecimal(50.07),
                longitude = BigDecimal(14.42),
                postalCode = "ExamplePostalCode",
                personId = UUID.fromString("812ffa1a-54d2-47be-b471-e2dcbb355f76")
            )
        )

        sleep(1000)

        assertThat(addressRepository.listAll()).satisfiesExactlyInAnyOrder({
            assertThat(it.id).isNotNull()
            assertThat(it.country).isEqualTo("ExampleCountry")
            assertThat(personRepository.getById(UUID.fromString("812ffa1a-54d2-47be-b471-e2dcbb355f76")))
                .isEqualTo(it.person)
        })
    }

}