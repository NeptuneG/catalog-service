package com.polarbookshop.orderservice.web

import com.polarbookshop.orderservice.domain.entities.Order
import com.polarbookshop.orderservice.domain.OrderService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("orders")
class OrderController(
    private val orderService: OrderService,
) {
    @GetMapping
    suspend fun getAllOrders(): Flux<Order> = orderService.getAllOrders()

    @PostMapping
    suspend fun submitOrder(
        @Valid @RequestBody orderRequest: OrderRequest,
    ): Mono<Order> = orderService.submitOrder(orderRequest.isbn, orderRequest.quantity)
}
