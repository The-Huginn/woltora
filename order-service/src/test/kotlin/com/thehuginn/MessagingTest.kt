package com.thehuginn

import com.thehuginn.utils.KafkaTestResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.reactive.messaging.memory.InMemoryConnector
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.spi.Connector

@QuarkusTest
@QuarkusTestResource(KafkaTestResource::class)
abstract class MessagingTest {

    @Inject
    @field:Connector("smallrye-in-memory")
    lateinit var connector: InMemoryConnector

}