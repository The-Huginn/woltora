package com.thehuginn.controller

import com.thehuginn.controller.dto.response.PersonResponse
import com.thehuginn.sql.QuarkusTestWithSql
import com.thehuginn.sql.Sql
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType.JSON
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

@QuarkusTest
@QuarkusTestWithSql
class PersonResourceTest {

    @Test
    @Sql(["sql/simple-person.sql", "sql/simple-address.sql"])
    fun testGetPersonById() {
        val personId = UUID.fromString("812ffa1a-54d2-47be-b471-e2dcbb355f76")

        val response = given()
            .pathParam("id", personId)
            .`when`()
            .get("/person/{id}")
            .then()
            .statusCode(200)
            .contentType(JSON)
            .extract().body().`as`(PersonResponse::class.java)

        assertThat(response.id).isEqualTo(personId)
        assertThat(response.firstName).isEqualTo("Jano")
    }

    @Test
    @Sql(["sql/simple-person.sql", "sql/simple-address.sql"])
    fun testGetPersonByName() {
        val personId = UUID.fromString("812ffa1a-54d2-47be-b471-e2dcbb355f76")
        val firstName = "Jano"
        val lastName = "Oznuk"

        val response = given()
            .queryParam("firstName", firstName)
            .queryParam("lastName", lastName)
            .`when`()
            .get("/person")
            .then()
            .statusCode(200)
            .contentType(JSON)
            .extract().body().`as`(Array<PersonResponse>::class.java)

        assertThat(response).hasSize(1)

        assertThat(response[0].id).isEqualTo(personId)
    }

}