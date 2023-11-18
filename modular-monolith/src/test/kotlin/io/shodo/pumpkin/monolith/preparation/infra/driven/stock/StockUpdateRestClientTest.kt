package io.shodo.pumpkin.monolith.preparation.infra.driven.stock

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@Tag("Integration")
@SpringBootTest
@ContextConfiguration(classes = [StockUpdateRestClientTest.Config::class])
class StockUpdateRestClientTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = ["io.shodo.pumpkin.monolith.preparation.infra.driven.stock"])
    internal class Config

    @Autowired
    private lateinit var client: StockUpdateRestClient

    companion object {
        private val mockServer = MockWebServer()

        @JvmStatic
        @DynamicPropertySource
        fun registerBaseUrl(registry: DynamicPropertyRegistry) {
            registry.add("application.preparation.infra.stock.base-url") { "http://localhost:${mockServer.port}" }
        }

        @JvmStatic
        @BeforeAll
        fun setUpMockServer() = mockServer.start()

        @JvmStatic
        @AfterAll
        fun tearDownMockServer() = mockServer.shutdown()
    }

    @Test
    fun should_notify_consumption_to_Stock_API() {
        mockServer.enqueue(MockResponse().setResponseCode(204))
        val expectedRequestBody = """{
                "ingredient": "Milk",
                "usedQuantity": {
                    "amount":289,
                    "unitOfMeasure":"cL"
                }
            }""".trimIndent()

        client.notifyConsumption(StockUpdateRequestBody("Milk", QuantityField(289, "cL"))).block()

        val request = mockServer.takeRequest()
        assertSoftly {
            it.assertThat(request.method).isEqualTo("POST")
            it.assertThat(request.path).isEqualTo("/use")
            it.assertThat(request.getHeader(CONTENT_TYPE)).isEqualTo(APPLICATION_JSON_VALUE)
        }
        assertEquals(request.body.readUtf8(), expectedRequestBody, true)
    }

    @Test
    fun should_fail_to_notify_consumption_to_Stock_API_on_4xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(400))

        assertThatExceptionOfType(StockUpdateFailureException::class.java)
            .isThrownBy { client.notifyConsumption(aValidStockUpdateRequestBody()).block() }
            .withMessage("Stock update failed - Server responded with client error code 400 BAD_REQUEST")
    }

    @Test
    fun should_fail_to_notify_consumption_to_Stock_API_on_5xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(500))

        assertThatExceptionOfType(StockUpdateFailureException::class.java)
            .isThrownBy { client.notifyConsumption(aValidStockUpdateRequestBody()).block() }
            .withMessage("Stock update failed - Server responded with server error code 500 INTERNAL_SERVER_ERROR")
    }

    private fun aValidStockUpdateRequestBody() = StockUpdateRequestBody("XXX", QuantityField(1, "g"))

}
