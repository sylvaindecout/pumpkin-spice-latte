package io.shodo.pumpkin.monolith.ordering.infra.driven.stock

import io.shodo.pumpkin.monolith.ordering.domain.StockAccessFailureException
import io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client.QuantityField
import io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client.StockAccessRestClient
import io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client.StockItemResponseBody
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

class StockAdapterTest {

    private val client = mock<StockAccessRestClient>()
    private val adapter = StockAdapter(client)

    @Test
    fun should_consider_stock_as_sufficient_if_required_quantity_is_equals_to_available_quantity() {
        val ingredient = Ingredient("Coffee beans")
        val response = StockItemResponseBody("Milk", QuantityField(50, "g"))
        given(client.find(ingredient)).willReturn(response)

        val result = adapter.hasEnoughOf(ingredient, grams(50))

        assertThat(result).isTrue
    }

    @Test
    fun should_consider_that_stock_is_insufficient_if_required_quantity_is_greater_than_available_quantity() {
        val ingredient = Ingredient("Coffee beans")
        val response = StockItemResponseBody("Milk", QuantityField(49, "g"))
        given(client.find(ingredient)).willReturn(response)

        val result = adapter.hasEnoughOf(ingredient, grams(50))

        assertThat(result).isFalse
    }

    @Test
    fun should_consider_that_stock_is_insufficient_if_unit_of_measure_is_inconsistent() {
        val ingredient = Ingredient("Coffee beans")
        val response = StockItemResponseBody("Milk", QuantityField(50, "cL"))
        given(client.find(ingredient)).willReturn(response)

        val result = adapter.hasEnoughOf(ingredient, grams(50))

        assertThat(result).isFalse
    }

    @Test
    fun should_fail_to_check_if_stock_is_sufficient_if_service_call_fails() {
        val ingredient = Ingredient("Coffee beans")
        given(client.find(ingredient)).willThrow(StockAccessFailureException("failure"))

        assertThatExceptionOfType(StockAccessFailureException::class.java)
            .isThrownBy { adapter.hasEnoughOf(ingredient, grams(50)) }
            .withMessage("Stock access failed - failure")
    }

}
