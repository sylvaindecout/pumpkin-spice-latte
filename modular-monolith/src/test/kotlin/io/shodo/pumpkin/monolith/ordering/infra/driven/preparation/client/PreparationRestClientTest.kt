package io.shodo.pumpkin.monolith.ordering.infra.driven.preparation.client

import io.shodo.pumpkin.monolith.ordering.domain.preparation.PreparationFailureException
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
@ContextConfiguration(classes = [PreparationRestClientTest.Config::class])
class PreparationRestClientTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = ["io.shodo.pumpkin.monolith.ordering.infra.driven.preparation.client"])
    internal class Config

    @Autowired
    private lateinit var client: PreparationRestClient

    companion object {
        private val mockServer = MockWebServer()

        @JvmStatic
        @DynamicPropertySource
        fun registerBaseUrl(registry: DynamicPropertyRegistry) {
            registry.add("application.ordering.infra.preparation.base-url") { "http://localhost:${mockServer.port}" }
        }

        @JvmStatic
        @BeforeAll
        fun setUpMockServer() = mockServer.start()

        @JvmStatic
        @AfterAll
        fun tearDownMockServer() = mockServer.shutdown()
    }

    @Test
    fun should_queue_drink_in_Preparation_API() {
        mockServer.enqueue(MockResponse().setResponseCode(204))
        val expectedRequestBody = """{
                "name": "LATTE",
                "ingredients": {
                    "Coffee beans": {
                        "amount":7,
                        "unitOfMeasure":"g"
                    },
                    "Milk": {
                        "amount":5,
                        "unitOfMeasure":"cL"
                    }
                },
                "customer": "Vincent"
            }""".trimIndent()

        client.queue(
            DrinkRequestBody(
                "LATTE", mapOf(
                    "Coffee beans" to QuantityField(7, "g"),
                    "Milk" to QuantityField(5, "cL")
                ), "Vincent"
            )
        ).block()

        val request = mockServer.takeRequest()
        assertSoftly {
            it.assertThat(request.method).isEqualTo("POST")
            it.assertThat(request.path).isEqualTo("/drinks")
            it.assertThat(request.getHeader(CONTENT_TYPE)).isEqualTo(APPLICATION_JSON_VALUE)
        }
        assertEquals(request.body.readUtf8(), expectedRequestBody, true)
    }

    @Test
    fun should_fail_to_queue_drink_in_Preparation_API_on_4xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(400))

        assertThatExceptionOfType(PreparationFailureException::class.java)
            .isThrownBy { client.queue(aValidDrinkRequestBody()).block() }
            .withMessage("Preparation notification failed - Server responded with client error code 400 BAD_REQUEST")
        mockServer.takeRequest()
    }

    @Test
    fun should_fail_to_queue_drink_in_Preparation_API_on_5xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(500))

        assertThatExceptionOfType(PreparationFailureException::class.java)
            .isThrownBy { client.queue(aValidDrinkRequestBody()).block() }
            .withMessage("Preparation notification failed - Server responded with server error code 500 INTERNAL_SERVER_ERROR")
        mockServer.takeRequest()
    }

    private fun aValidDrinkRequestBody() = DrinkRequestBody("XXX", mapOf(), "XXX")

}
