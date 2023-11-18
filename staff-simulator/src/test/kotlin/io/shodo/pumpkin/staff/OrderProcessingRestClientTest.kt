package io.shodo.pumpkin.staff

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@Tag("Integration")
@SpringBootTest
@ContextConfiguration(classes = [OrderProcessingRestClientTest.Config::class])
class OrderProcessingRestClientTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = ["io.shodo.pumpkin.staff"])
    internal class Config

    @Autowired
    private lateinit var client: OrderProcessingRestClient

    companion object {
        private val mockServer = MockWebServer()

        @JvmStatic
        @DynamicPropertySource
        fun registerBaseUrl(registry: DynamicPropertyRegistry) {
            registry.add("application.staff-simulator.infra.preparation.base-url") { "http://localhost:${mockServer.port}" }
        }

        @JvmStatic
        @BeforeAll
        fun setUpMockServer() = mockServer.start()

        @JvmStatic
        @AfterAll
        fun tearDownMockServer() = mockServer.shutdown()
    }

    @Test
    fun should_send_processing_notification_to_Preparation_API() {
        mockServer.enqueue(MockResponse().setResponseCode(204))

        client.processNextOrder().block()

        val request = mockServer.takeRequest()
        assertSoftly {
            it.assertThat(request.method).isEqualTo("POST")
            it.assertThat(request.path).isEqualTo("/drinks/take")
        }
    }

    @Test
    fun should_fail_to_send_processing_notification_to_Preparation_API_on_4xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(400))

        assertThatExceptionOfType(OrderProcessingException::class.java)
            .isThrownBy { client.processNextOrder().block() }
            .withMessage("Processing next order failed - Server responded with client error code 400 BAD_REQUEST")
    }

    @Test
    fun should_fail_to_send_processing_notification_to_Preparation_API_on_5xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(500))

        assertThatExceptionOfType(OrderProcessingException::class.java)
            .isThrownBy { client.processNextOrder().block() }
            .withMessage("Processing next order failed - Server responded with server error code 500 INTERNAL_SERVER_ERROR")
    }

}
