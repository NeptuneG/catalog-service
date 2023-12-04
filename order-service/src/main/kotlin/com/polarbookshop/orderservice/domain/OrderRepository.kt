package com.polarbookshop.orderservice.domain

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface OrderRepository : ReactiveCrudRepository<Order, Long>
