package io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client

import io.shodo.pumpkin.monolith.ordering.domain.menu.MenuAccessFailureException
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
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
@ContextConfiguration(classes = [MenuAccessRestClientTest.Config::class])
class MenuAccessRestClientTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = ["io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client"])
    internal class Config

    @Autowired
    private lateinit var client: MenuAccessRestClient

    companion object {
        private val mockServer = MockWebServer()

        @JvmStatic
        @DynamicPropertySource
        fun registerBaseUrl(registry: DynamicPropertyRegistry) {
            registry.add("application.ordering.infra.menu.base-url") { "http://localhost:${mockServer.port}" }
        }

        @JvmStatic
        @BeforeAll
        fun setUpMockServer() = mockServer.start()

        @JvmStatic
        @AfterAll
        fun tearDownMockServer() = mockServer.shutdown()
    }

    @Test
    fun should_find_drink_in_Menu_API() {
        mockServer.enqueue(
            MockResponse()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody(
                    """{
                   "name":"LATTE",
                    "unitPrice": {
                        "amountMinor":254950,
                        "currencyUnit":"EUR",
                        "scale":2
                    },
                    "ingredients": {
                        "Coffee beans": {
                            "amount":7,
                            "unitOfMeasure":"g"
                        },
                        "Milk": {
                            "amount":5,
                            "unitOfMeasure":"cL"
                        }
                    }
                }""".trimIndent()
                )
        )

        val result = client.find(DrinkName("LATTE"))

        val request = mockServer.takeRequest()
        assertSoftly {
            it.assertThat(request.method).isEqualTo("GET")
            it.assertThat(request.path).isEqualTo("/drinks/LATTE")
            it.assertThat(result).isEqualTo(
                MenuItemResponseBody(
                    name = "LATTE",
                    unitPrice = MoneyField(2549_50, "EUR", 2),
                    ingredients = mapOf(
                        "Coffee beans" to QuantityField(7, "g"),
                        "Milk" to QuantityField(5, "cL")
                    )
                )
            )
        }
    }

    @Test
    fun should_fail_to_find_drink_in_Menu_API_on_4xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(400))

        assertThatExceptionOfType(MenuAccessFailureException::class.java)
            .isThrownBy { client.find(DrinkName("LATTE")) }
            .withMessage("Menu access failed - Server responded with client error code 400 BAD_REQUEST")
    }

    @Test
    fun should_fail_to_find_drink_in_Menu_API_on_5xx_error() {
        mockServer.enqueue(MockResponse().setResponseCode(500))

        assertThatExceptionOfType(MenuAccessFailureException::class.java)
            .isThrownBy { client.find(DrinkName("LATTE")) }
            .withMessage("Menu access failed - Server responded with server error code 500 INTERNAL_SERVER_ERROR")
    }

    @Test
    fun should_fail_to_find_drink_in_Menu_API_on_invalid_response_body() {
        mockServer.enqueue(
            MockResponse()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody(
                    """{
                   "name":"",
                    "unitPrice": {
                        "amountMinor":254950,
                        "currencyUnit":"EUR",
                        "scale":2
                    },
                    "ingredients": {
                        "Coffee beans": {
                            "amount":7,
                            "unitOfMeasure":"g"
                        },
                        "Milk": {
                            "amount":-1,
                            "unitOfMeasure":"cL"
                        }
                    }
                }"""
                )
        )

        assertThatExceptionOfType(MenuAccessFailureException::class.java)
            .isThrownBy { client.find(DrinkName("LATTE")) }
            .withMessage("Menu access failed - Server responded with unexpected format - ingredients[Milk].amount (-1) must be greater than 0, name () must not be blank")
    }

    @Test
    fun should_fail_to_find_drink_in_Menu_API_on_empty_response_body() {
        mockServer.enqueue(
            MockResponse()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .setBody("")
        )

        assertThatExceptionOfType(MenuAccessFailureException::class.java)
            .isThrownBy { client.find(DrinkName("LATTE")) }
            .withMessageStartingWith("Menu access failed - ")
    }

}
