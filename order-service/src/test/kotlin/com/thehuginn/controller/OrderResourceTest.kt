package com.thehuginn.controller

import com.thehuginn.controller.dto.response.OrderResponse
import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.sql.QuarkusTestWithSql
import com.thehuginn.sql.Sql
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestWithSql
class OrderResourceTest {

    @Test
    @Sql(["sql/simple-order.sql"])
    fun testGetOrderById() {
        val orderId = UUID.fromString("278ba540-d2e7-4f0c-862d-a6b6e5180338")

        val response = given()
            .pathParam("id", orderId)
            .`when`()
            .get("/order/{id}")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract().body().`as`(OrderResponse::class.java)

        assertThat(response.id).isEqualTo(orderId)
        assertThat(response.items).containsExactlyInAnyOrder(UUID.fromString("f1e98890-74d1-4800-8347-7f2e68fc9bfc"))
        assertThat(response.userId).isEqualTo(UUID.fromString("5ff13a5c-ef93-4096-8507-21ab93d1f894"))
        assertThat(response.restaurantId).isEqualTo(UUID.fromString("6085ce48-da17-42ab-bef7-ee34f60f0762"))
        assertThat(response.status).isEqualTo(CREATED)
    }
}