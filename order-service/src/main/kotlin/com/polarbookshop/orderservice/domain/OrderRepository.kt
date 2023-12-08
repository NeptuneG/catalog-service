package com.polarbookshop.orderservice.domain

import com.polarbookshop.orderservice.domain.entities.Order
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface OrderRepository : ReactiveCrudRepository<Order, Long>
