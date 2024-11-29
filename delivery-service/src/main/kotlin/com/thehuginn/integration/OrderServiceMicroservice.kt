package com.thehuginn.integration

import com.thehuginn.integration.dto.OrderResponse
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import java.util.UUID
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.resteasy.reactive.RestQuery

@Path("/api/order")
@RegisterRestClient
interface OrderServiceMicroservice {

    @GET
    fun getOrdersByUserId(@RestQuery userId: UUID): List<OrderResponse>

}