package com.thehuginn.measurement;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import java.time.Instant;

import static java.time.Instant.now;

@Measurement(name = "order_location")
public class OrderLocation{
    @Column(name = "_value") public String location;
    @Column(tag = true) public String orderId;
    @Column(timestamp = true) public Instant timestamp;

    public OrderLocation() {}

    public OrderLocation(String location, String orderId) {
        this.location = location;
        this.orderId = orderId;
        this.timestamp = now();
    }
}
