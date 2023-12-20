package com.polarbookshop.dispatcherservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.polarbookshop.dispatcherservice.entities.OrderAcceptedMessage
import com.polarbookshop.dispatcherservice.entities.OrderDispatchedMessage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
import org.springframework.context.annotation.Import
import org.springframework.messaging.support.MessageBuilder


@SpringBootTest
@Import(TestChannelBinderConfiguration::class)
class FunctionsStreamIntegrationTests(
    @Autowired private val input: InputDestination,
    @Autowired private val output: OutputDestination,
    @Autowired private val objectMapper: ObjectMapper,
) {
    @Test
    fun whenOrderAcceptedThenDispatched() {
        val orderId = 123L
        val inputMessage = MessageBuilder.withPayload(OrderAcceptedMessage(orderId)).build()
        val expected = OrderDispatchedMessage(orderId)

        input.send(inputMessage)

        val actual: OrderDispatchedMessage = objectMapper.readValue(output.receive().payload)
        assertThat(actual).isEqualTo(expected)
    }

    private inline fun <reified T> ObjectMapper.readValue(bytes: ByteArray): T = readValue(bytes, T::class.java)
}
