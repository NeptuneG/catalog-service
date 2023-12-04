package com.polarbookshop.orderservice.domain

import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val orderRepository: OrderRepository,
) {
    suspend fun getAllOrders(): Flux<Order> = orderRepository.findAll()
    suspend fun submitOrder(isbn: String, quantity: Int): Mono<Order> = mono {
        Order.buildRejected(isbn, quantity)
    }.flatMap { order ->
        orderRepository.save(order)
    }
}
