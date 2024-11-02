package com.thehuginn.repositories

import com.thehuginn.domain.Order
import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<Order, UUID>