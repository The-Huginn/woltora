package com.thehuginn.repository

import com.thehuginn.domain.Restaurant
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class RestaurantRepository : PanacheRepositoryBase<Restaurant, UUID>