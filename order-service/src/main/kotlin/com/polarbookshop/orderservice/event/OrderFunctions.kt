package com.polarbookshop.orderservice.event

import com.polarbookshop.orderservice.domain.OrderService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux

@Configuration
class OrderFunctions {
    companion object {
        private val logger = LoggerFactory.getLogger(OrderFunctions::class.java)
    }

    @Bean
    fun dispatchOrder(orderService: OrderService): (Flux<OrderDispatchedMessage>) -> Unit = { flux ->
        orderService.consumeOrderDispatchedEvent(flux)
            .doOnNext { order ->
                logger.info("The order with id ${order.id} is dispatched")
            }
            .subscribe()
    }
}
