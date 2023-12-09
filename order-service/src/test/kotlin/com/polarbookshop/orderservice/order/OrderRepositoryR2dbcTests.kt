package com.polarbookshop.orderservice.order

import com.polarbookshop.orderservice.config.DataConfig
import com.polarbookshop.orderservice.domain.OrderRepository
import com.polarbookshop.orderservice.domain.OrderStatus
import com.polarbookshop.orderservice.domain.entities.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import reactor.test.StepVerifier


@DataR2dbcTest
@Import(DataConfig::class)
@Testcontainers
class OrderRepositoryR2dbcTests(
    @Autowired
    private val orderRepository: OrderRepository
) {
    companion object {
        @Container
        val postgresql = PostgreSQLContainer(DockerImageName.parse("postgres:16.1-alpine3.18"))

        @DynamicPropertySource
        fun postgresqlProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.r2dbc.url") { r2dbcUrl }
            registry.add("spring.r2dbc.username") { postgresql.username }
            registry.add("spring.r2dbc.password") { postgresql.password }
            registry.add("spring.flyway.url") { postgresql.jdbcUrl }
        }

        @Suppress("MaxLineLength")
        private val r2dbcUrl: String get() = "r2dbc:postgresql://${postgresql.host}:${postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)}/${postgresql.databaseName}"
    }

    @Test
    fun createRejectedOrder() {
        val rejectedOrder = Order.buildRejected("1234567890", 3)
        StepVerifier
            .create(orderRepository.save(rejectedOrder))
            .expectNextMatches { order ->
                order.status == OrderStatus.REJECTED
            }
            .verifyComplete()
    }
}
