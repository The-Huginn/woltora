package com.thehuginn.messaging.dto

import com.thehuginn.messaging.dto.command.CreateOrderCommandMessage
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

// FIXME this is here for debugging, when quarkus complaings about missing deserializer...
class Deserializer : ObjectMapperDeserializer<CreateOrderCommandMessage>(CreateOrderCommandMessage::class.java)