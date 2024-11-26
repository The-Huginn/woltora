package com.thehuginn.messaging

import com.thehuginn.messaging.dto.command.CreateOrderCommandMessage
import com.thehuginn.messaging.dto.event.mapper.OrderCreatedEventMessageMapper
import com.thehuginn.repository.MessageRepository.Message.CREATE_ORDER
import com.thehuginn.repository.MessageRepository.Message.ORDER_CREATED
import com.thehuginn.service.OrderService
import com.thehuginn.service.command.mapper.OrderCommandMapper
import io.smallrye.mutiny.Multi
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing

@ApplicationScoped
class CreateOrderCommandConsumer(
    private val orderService: OrderService,
    private val orderCommandMapper: OrderCommandMapper,
    private val orderCreatedEventMessageMapper: OrderCreatedEventMessageMapper
) {

    @Incoming(CREATE_ORDER)
    @Outgoing(ORDER_CREATED)
    fun process(message: CreateOrderCommandMessage) =
        Multi.createFrom().item(message)
            .map {
                orderService.persist(orderCommandMapper.mapFrom(it))
                    .let(orderCreatedEventMessageMapper::mapFrom)
            }

}