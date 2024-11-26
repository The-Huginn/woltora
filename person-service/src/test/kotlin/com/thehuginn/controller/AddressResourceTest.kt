package com.thehuginn.controller

import com.thehuginn.controller.dto.response.AddressResponse
import com.thehuginn.sql.QuarkusTestWithSql
import com.thehuginn.sql.Sql
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import org.assertj.core.api.Assertions.assertThat
import java.util.UUID

@QuarkusTest
@QuarkusTestWithSql
class AddressResourceTest {

    @Test
    @Sql(["sql/simple-person.sql", "sql/simple-address.sql"])
    fun testGetAddressById() {
        val addressId = UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed12b")

        val response = given()
            .pathParam("id", addressId)
            .`when`()
            .get("/address/{id}")
            .then()
            .statusCode(200)
            .contentType(JSON)
            .extract().body().`as`(AddressResponse::class.java)

        assertThat(response.id).isEqualTo(addressId)
        assertThat(response.street).isEqualTo("Purkynova")
    }

    @Test
    @Sql(["sql/simple-person.sql", "sql/simple-address.sql"])
    fun testGetAddressByUserId() {
        val userId = UUID.fromString("812ffa1a-54d2-47be-b471-e2dcbb355f77")
        val addressId = UUID.fromString("10a3edc2-0c83-45f3-9345-f27f828ed23c")

        val response = given()
            .queryParam("userId", userId)
            .`when`()
            .get("/address")
            .then()
            .statusCode(200)
            .contentType(JSON)
            .extract().body().`as`(Array<AddressResponse>::class.java)

        assertThat(response).hasSize(1)

        assertThat(response[0].id).isEqualTo(addressId)
    }

}