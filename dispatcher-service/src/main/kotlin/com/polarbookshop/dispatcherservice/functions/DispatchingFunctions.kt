package com.polarbookshop.dispatcherservice.functions

import com.polarbookshop.dispatcherservice.entities.OrderAcceptedMessage
import com.polarbookshop.dispatcherservice.entities.OrderDispatchedMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux

@Configuration
class DispatchingFunctions {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DispatchingFunctions::class.java)
    }

    @Bean
    fun pack(): (OrderAcceptedMessage) -> Long = { orderAcceptedMessage ->
        orderAcceptedMessage.orderId.apply {
            logger.info("The order with id ${orderAcceptedMessage.orderId} is packed.")
        }
    }

    @Bean
    fun label(): (Flux<Long>) -> Flux<OrderDispatchedMessage> = { orderFlux ->
        orderFlux.map { orderId ->
            OrderDispatchedMessage(orderId).apply {
                logger.info("The order with id $orderId is labeled.")
            }
        }
    }
}
