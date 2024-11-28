package com.thehuginn.domain

import com.thehuginn.enums.OrderStatus
import com.thehuginn.enums.OrderStatus.CREATED
import com.thehuginn.utils.DEFAULT_UUID
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant
import java.time.Instant.now
import java.util.UUID
import java.util.UUID.randomUUID
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType.ALL
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction.CASCADE
import org.hibernate.type.SqlTypes.NAMED_ENUM

@Entity
@Table(name =  "orders")
data class Order(

    @Enumerated(STRING)
    @JdbcTypeCode(NAMED_ENUM)
    var status: OrderStatus,

    val userId: UUID,

    @OnDelete(action = CASCADE)
    @ManyToOne
    var restaurant: Restaurant
) {
    @Id
    val id: UUID = randomUUID()

    val creationTimestamp: Instant = now()

    @Cascade(ALL)
    @ElementCollection(fetch = EAGER)
    @Column(name = "item")
    @CollectionTable(
        name = "order_items",
        joinColumns = [JoinColumn(name = "id")],
        foreignKey = ForeignKey(
            name = "fk_orders_order_items",
            foreignKeyDefinition = "foreign key (id) references orders (id) on delete cascade")
    )
    val items: MutableList<UUID> = mutableListOf()

    constructor() : this(
        status = CREATED,
        userId = DEFAULT_UUID,
        restaurant = Restaurant()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}