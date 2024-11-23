package com.thehuginn.repository

import com.thehuginn.domain.Food
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class FoodRepository : PanacheMongoRepositoryBase<Food, UUID> {

    fun findAllByRestaurantId(restaurantId: UUID) : List<Food> = list(Food::restaurantId.name, restaurantId)

}