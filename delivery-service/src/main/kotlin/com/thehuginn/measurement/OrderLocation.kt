package com.thehuginn.measurement

import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import java.time.Instant
import java.util.UUID

@Measurement(name = "order_location")
data class OrderLocation(
    @Column val location: String,
    @Column(tag = true) val orderId: UUID,
    @Column(timestamp = true) val timestamp: Instant
)
