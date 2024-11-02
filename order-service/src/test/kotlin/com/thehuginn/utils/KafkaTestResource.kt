package com.thehuginn.utils

import com.thehuginn.repository.MessageRepository
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.smallrye.reactive.messaging.memory.InMemoryConnector

class KafkaTestResource(
) : QuarkusTestResourceLifecycleManager {

    private val messageRepository = MessageRepository()
    override fun start(): Map<String, String> {
        return messageRepository.getAllMessages()
            .map { InMemoryConnector.switchIncomingChannelsToInMemory(it) }
            .associate { it.entries.first().toPair() }
    }

    override fun stop() {
        InMemoryConnector.clear();
    }
}