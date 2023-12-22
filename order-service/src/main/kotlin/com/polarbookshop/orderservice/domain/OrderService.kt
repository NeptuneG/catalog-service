package com.polarbookshop.orderservice.domain

import com.polarbookshop.orderservice.book.BookClient
import com.polarbookshop.orderservice.domain.entities.Order
import com.polarbookshop.orderservice.event.OrderAcceptedMessage
import com.polarbookshop.orderservice.event.OrderDispatchedMessage
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val bookClient: BookClient,
    private val orderRepository: OrderRepository,
    private val streamBridge: StreamBridge,
) {
    suspend fun getAllOrders(): Flux<Order> = orderRepository.findAll()

    @Transactional
    fun submitOrder(isbn: String, quantity: Int): Mono<Order> {
        return bookClient.getBookByIsbn(isbn)
            .map { book ->
                Order.buildAccepted(book, quantity)
            }
            .defaultIfEmpty(Order.buildRejected(isbn, quantity))
            .flatMap(orderRepository::save)
            .doOnNext(::publishOrderAcceptEvent)
    }

    fun consumeOrderDispatchedEvent(flux: Flux<OrderDispatchedMessage>): Flux<Order> {
        return flux
            .flatMap {
                orderRepository.findById(it.orderId)
            }
            .map {
                it.dispatch()
            }.flatMap {
                orderRepository.save(it)
            }
    }

    private fun publishOrderAcceptEvent(order: Order) {
        if (order.status != OrderStatus.ACCEPTED) {
            return
        }

        val orderAcceptedMessage = OrderAcceptedMessage(order.id!!)
        logger.info("Sending order accepted event with id: ${order.id}")
        val result = streamBridge.send("acceptOrder-out-0", orderAcceptedMessage)
        logger.info("Result of sending data for order with id ${order.id}: $result")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(OrderService::class.java)
    }
}
