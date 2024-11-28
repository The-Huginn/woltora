package com.thehuginn.messaging

import com.thehuginn.messaging.dto.command.CreateOrderCommandMessage
import com.thehuginn.messaging.dto.event.mapper.OrderCreatedEventMessageMapper
import com.thehuginn.repository.MessageRepository.Message.CREATE_ORDER
import com.thehuginn.repository.MessageRepository.Message.ORDER_CREATED
import com.thehuginn.service.OrderService
import com.thehuginn.service.command.mapper.OrderCommandMapper
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import org.jboss.logging.Logger.getLogger

@ApplicationScoped
class CreateOrderCommandConsumer(
    private val orderService: OrderService,
    private val orderCommandMapper: OrderCommandMapper,
    private val orderCreatedEventMessageMapper: OrderCreatedEventMessageMapper
) {
    private val logger = getLogger(CreateOrderCommandConsumer::class.java.toString())

    @Incoming(CREATE_ORDER)
    @Outgoing(ORDER_CREATED)
    fun process(message: CreateOrderCommandMessage) =
        Multi.createFrom().item(message)
            .onItem().transformToUniAndMerge {
                Uni.createFrom().voidItem()
                    .flatMap { ignored -> orderService.persist(orderCommandMapper.mapFrom(it)) }
                    .invoke { it -> logger.info("Order ${it.id} created successfully")}
                    .map { orderCreatedEventMessageMapper.mapFrom(it) }
            }
            .onFailure().invoke { throwable -> logger.info("Error during message processing $throwable")}
}