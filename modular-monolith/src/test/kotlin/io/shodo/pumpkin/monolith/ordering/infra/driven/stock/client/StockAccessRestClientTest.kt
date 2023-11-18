package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import io.shodo.pumpkin.monolith.ordering.domain.StockAccessFailureException
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
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
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@Tag("Integration")
@SpringBootTest
@ContextConfiguration(classes = [StockAccessRestClientTest.Config::class])
class StockAccessRestClientTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = ["io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client"])
    internal class Config

    @Autowired
    private lateinit var client: StockAccessRestClient

    companion object {
        private val mockServer = MockWebServer()

        @JvmStatic
        @DynamicPropertySource
        fun registerBaseUrl(registry: DynamicPropertyRegistry) {
            registry.add("application.ordering.infra.stock.base-url") { "http://localhost:${mockServer.port}" }
        }

        @JvmStatic
        @BeforeAll
        fun setUpMockServer() = mockServer.start()

        @JvmStatic
        @AfterAll
        fun tearDownMockServer() = mockServer.shutdown()
    }

    @Test
    fun should_find_ingredient_in_Stock_API() {
        mockServer.enqueue(
            MockResponse()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody(
                    """{
                    "ingredient": "Milk",
                    "currentQuantity": {
                        "amount": 290,
                        "unitOfMeasure": "cL"
                    }
                }""".trimIndent()
                )
        )

        val result = client.find(Ingredient("Milk"))

        val request = mockServer.takeRequest()
        assertSoftly {
            it.assertThat(request.method).isEqualTo("GET")
            it.assertThat(request.path).isEqualTo("/ingredients/Milk")
            it.assertThat(result).isEqualTo(
                StockItemResponseBody(
                    ingredient = "Milk",
                    currentQuantity = QuantityField(290, "cL")
                )
            )
        }
    }

    @Test
    fun should_fail_to_find_ingredient_in_Stock_API_on_4xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(400))

        assertThatExceptionOfType(StockAccessFailureException::class.java)
            .isThrownBy { client.find(Ingredient("Milk")) }
            .withMessage("Stock access failed - Server responded with client error code 400 BAD_REQUEST")
    }

    @Test
    fun should_fail_to_find_ingredient_in_Stock_API_on_5xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(500))

        assertThatExceptionOfType(StockAccessFailureException::class.java)
            .isThrownBy { client.find(Ingredient("Milk")) }
            .withMessage("Stock access failed - Server responded with server error code 500 INTERNAL_SERVER_ERROR")
    }

    @Test
    fun should_fail_to_find_ingredient_in_Stock_API_on_invalid_response_body() {
        mockServer.enqueue(
            MockResponse()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody(
                    """{
                    "ingredient": "Milk",
                    "currentQuantity": {
                        "amount": -1,
                        "unitOfMeasure": "cL"
                    }
                }""".trimIndent()
                )
        )

        assertThatExceptionOfType(StockAccessFailureException::class.java)
            .isThrownBy { client.find(Ingredient("Milk")) }
            .withMessage("Stock access failed - Server responded with unexpected format - currentQuantity.amount (-1) must be greater than 0")
    }

    @Test
    fun should_fail_to_find_ingredient_in_Stock_API_on_empty_response_body() {
        mockServer.enqueue(
            MockResponse()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody("")
        )

        assertThatExceptionOfType(StockAccessFailureException::class.java)
            .isThrownBy { client.find(Ingredient("Milk")) }
            .withMessage("Stock access failed - Source was empty")
    }

}
