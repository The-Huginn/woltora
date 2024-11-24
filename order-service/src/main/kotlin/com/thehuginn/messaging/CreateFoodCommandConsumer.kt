package com.thehuginn.messaging

import com.thehuginn.domain.Food
import com.thehuginn.domain.Restaurant
import com.thehuginn.exception.NotFoundException
import com.thehuginn.messaging.dto.CreateFoodCommandMessage
import com.thehuginn.repository.FoodRepository
import com.thehuginn.repository.MessageRepository.Message.CREATE_FOOD
import com.thehuginn.repository.RestaurantRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logmanager.Logger

@ApplicationScoped
class CreateFoodCommandConsumer(
    val restaurantRepository: RestaurantRepository,
    val foodRepository: FoodRepository
) {

    private val logger = Logger.getLogger(CreateFoodCommandConsumer::class.java.toString())

    @Incoming(CREATE_FOOD)
    @Transactional
    fun process(message: CreateFoodCommandMessage): Uni<Void> =
        Uni.createFrom().item(message)
            .onItem().transformToUni { msg ->
                logger.info("Processing message: $msg")
                Uni.createFrom().item { restaurantRepository.findById(msg.restaurantId) }
                    .onItem().ifNull().failWith(NotFoundException(Restaurant::class.java, msg.restaurantId))
                    .map {
                        Food(
                            restaurantId = msg.restaurantId,
                            name = msg.name,
                            description = msg.description,
                            price = msg.price,
                            available = msg.available
                        )
                    }
            }
            .chain { food -> Uni.createFrom().item(foodRepository.persist(food)) }
            .onFailure().invoke { throwable -> logger.info("Error during message processing $throwable") }
            .replaceWithVoid()
}