package com.polarbookshop.orderservice.domain

import com.polarbookshop.orderservice.book.BookClient
import com.polarbookshop.orderservice.domain.entities.Order
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val bookClient: BookClient,
    private val orderRepository: OrderRepository,
) {
    suspend fun getAllOrders(): Flux<Order> = orderRepository.findAll()
    suspend fun submitOrder(isbn: String, quantity: Int): Mono<Order> {
        return bookClient.getBookByIsbn(isbn)
            .map { book ->
                Order.buildAccepted(book, quantity)
            }
            .defaultIfEmpty(Order.buildRejected(isbn, quantity))
            .flatMap { order ->
                orderRepository.save(order)
            }
    }
}
