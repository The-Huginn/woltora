package com.thehuginn.messaging

import com.thehuginn.messaging.dto.command.CreateFoodCommandMessage
import com.thehuginn.repository.FoodRepository
import com.thehuginn.repository.MessageRepository.Message.CREATE_FOOD
import com.thehuginn.sql.QuarkusTestWithSql
import com.thehuginn.sql.Sql
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import java.math.BigDecimal
import java.time.Duration
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestWithSql
class CreateFoodCommandMessageTest {

    @Inject
    lateinit var foodRepository: FoodRepository

    @Inject
    @Channel(CREATE_FOOD)
    lateinit var emitter: Emitter<CreateFoodCommandMessage>

    @Test
    @Sql(["sql/simple-restaurant.sql"])
    @RunOnVertxContext
    fun `should create food when message received`() {
        emitter.send(
            CreateFoodCommandMessage(
                name = "Test Food",
                description = "Test Food Description",
                restaurantId = UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed01a"),
                price = BigDecimal(100)
            )
        )

        Uni.createFrom().voidItem().onItem().delayIt().by(Duration.ofSeconds(1)).subscribe().with {
            assertThat(foodRepository.listAll()).satisfiesExactlyInAnyOrder({
                assertThat(it.id).isNotNull()
                assertThat(it.name).isEqualTo("Test Food")
                assertThat(it.description).isEqualTo("Test Food Description")
                assertThat(it.restaurantId).isEqualTo(UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed01a"))
                assertThat(it.price).isEqualTo(BigDecimal(100))
                assertThat(it.available).isTrue()
            })
        }
    }
}