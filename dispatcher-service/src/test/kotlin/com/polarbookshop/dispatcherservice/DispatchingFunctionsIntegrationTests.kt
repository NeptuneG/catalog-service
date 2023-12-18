package com.polarbookshop.dispatcherservice

import com.polarbookshop.dispatcherservice.entities.OrderAcceptedMessage
import com.polarbookshop.dispatcherservice.entities.OrderDispatchedMessage
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.function.context.FunctionCatalog
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.function.Function

@FunctionalSpringBootTest
class DispatchingFunctionsIntegrationTests(
    @Autowired private val catalog: FunctionCatalog,
) {
    @Test
    fun packAndLabelOrder() {
//        val packAndLabel: (OrderAcceptedMessage) -> Flux<OrderDispatchedMessage> = catalog.lookup("pack|label")
        val packAndLabel: Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> = catalog.lookup("pack|label")
        val orderId = 12L
        val orderAcceptedMessage = OrderAcceptedMessage(orderId)

        StepVerifier
//            .create(packAndLabel(orderAcceptedMessage))
            .create(packAndLabel.apply(orderAcceptedMessage))
            .expectNextMatches { dispatchedOrder ->
                dispatchedOrder.equals(OrderDispatchedMessage(orderId))
            }
            .verifyComplete()
    }
}
