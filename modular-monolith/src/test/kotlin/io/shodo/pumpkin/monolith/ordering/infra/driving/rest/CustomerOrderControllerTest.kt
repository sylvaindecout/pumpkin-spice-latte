package io.shodo.pumpkin.monolith.ordering.infra.driving.rest

import io.shodo.pumpkin.monolith.TestApp
import io.shodo.pumpkin.monolith.ordering.domain.*
import io.shodo.pumpkin.monolith.ordering.domain.catalogue.CatalogueAccessFailureException
import io.shodo.pumpkin.monolith.ordering.domain.preparation.PreparationFailureException
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import org.joda.money.CurrencyUnit.EUR
import org.joda.money.Money
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@Tag("Integration")
@SpringBootTest(classes = [TestApp::class])
@AutoConfigureMockMvc
class CustomerOrderControllerTest {

    @MockBean
    private lateinit var orderHandler: CustomerOrderHandler

    @Test
    fun should_process_order(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "drink": "LATTE",
            "quantity": 3,
            "customer": "Vincent"
        }""".trimIndent()
        val expectedResponseBody = """{
            "totalPrice": {
               "amountMinor": 1500,
               "currencyUnit": "EUR",
               "scale": 2
            }
        }""".trimIndent()
        given(orderHandler.process(Order(DrinkName("LATTE"), 3, Customer("Vincent"))))
            .willReturn(Invoice(DrinkName("LATTE"), 3, Money.of(EUR, 5.00)))

        mockMvc.post("/orders") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isOk() }
            content { json(expectedResponseBody) }
        }
    }

    @Test
    fun should_fail_to_process_order_if_drink_is_not_in_catalogue(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "drink": "BICYCLE",
            "quantity": 3,
            "customer": "Vincent"
        }""".trimIndent()
        given(orderHandler.process(any()))
            .willThrow(UnknownDrinkException(DrinkName("BICYCLE")))

        mockMvc.post("/orders") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun should_fail_to_process_order_if_ingredients_are_missing(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "drink": "LATTE",
            "quantity": 300,
            "customer": "Vincent"
        }""".trimIndent()
        given(orderHandler.process(any()))
            .willThrow(UnavailableIngredientException(Ingredient("Milk")))

        mockMvc.post("/orders") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isConflict() }
        }
    }

    @Test
    fun should_fail_to_process_order_if_catalogue_service_is_unavailable(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "drink": "LATTE",
            "quantity": 3,
            "customer": "Vincent"
        }""".trimIndent()
        given(orderHandler.process(any()))
            .willThrow(CatalogueAccessFailureException("Server responded with server error code 500"))

        mockMvc.post("/orders") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isServiceUnavailable() }
        }
    }

    @Test
    fun should_fail_to_process_order_if_preparation_service_is_unavailable(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "drink": "LATTE",
            "quantity": 3,
            "customer": "Vincent"
        }""".trimIndent()
        given(orderHandler.process(any()))
            .willThrow(PreparationFailureException("Server responded with server error code 500"))

        mockMvc.post("/orders") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isServiceUnavailable() }
        }
    }

    @Test
    fun should_fail_to_process_order_if_stock_service_is_unavailable(@Autowired mockMvc: MockMvc) {
        val requestBody = """{
            "drink": "LATTE",
            "quantity": 3,
            "customer": "Vincent"
        }""".trimIndent()
        given(orderHandler.process(any()))
            .willThrow(StockAccessFailureException("Server responded with server error code 500"))

        mockMvc.post("/orders") {
            contentType = APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isServiceUnavailable() }
        }
    }

}
