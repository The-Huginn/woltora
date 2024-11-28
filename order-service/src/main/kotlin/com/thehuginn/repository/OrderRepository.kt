package com.thehuginn.repository

import com.thehuginn.domain.Order
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class OrderRepository : PanacheRepositoryBase<Order, UUID> {

    fun getById(id: UUID) = findById(id)

}