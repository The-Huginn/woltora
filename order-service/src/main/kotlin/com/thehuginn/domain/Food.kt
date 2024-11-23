package com.thehuginn.domain

import io.quarkus.mongodb.panache.common.MongoEntity
import java.math.BigDecimal
import java.util.UUID
import java.util.UUID.randomUUID
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty

@MongoEntity
data class Food @BsonCreator constructor(
    @BsonProperty("restaurantId") val restaurantId: UUID,
    @BsonProperty("name") val name: String,
    @BsonProperty("description") val description: String? = null,
    @BsonProperty("price") val price: BigDecimal,
    @BsonProperty("available") val available: Boolean,
) {
    @BsonId val id: UUID = randomUUID()
}
