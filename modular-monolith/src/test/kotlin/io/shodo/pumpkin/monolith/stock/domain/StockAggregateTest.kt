package io.shodo.pumpkin.monolith.stock.domain

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.stock.infra.driven.database.InMemoryStockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StockAggregateTest {

    private val repository = InMemoryStockRepository()
    private val stock = StockAggregate(repository)

    @Test
    fun should_find_available_ingredient() {
        val stockItem = StockItem(Ingredient("Milk"), centiliters(1_000))
        repository.upsert(stockItem)

        val result = stock.find(stockItem.ingredient)

        assertThat(result).isEqualTo(stockItem)
    }

    @Test
    fun should_not_find_unavailable_ingredient() {
        val unavailableIngredient = Ingredient("Milk")
        repository.delete(unavailableIngredient)

        val result = stock.find(unavailableIngredient)

        assertThat(result).isNull()
    }

    @Test
    fun should_use_quantity_of_ingredient_with_more_quantity_in_stock_than_necessary() {
        val ingredient = Ingredient("Milk")
        repository.upsert(StockItem(ingredient, centiliters(1_000)))

        stock.use(ingredient, centiliters(5))

        assertThat(repository.find(ingredient))
            .isEqualTo(StockItem(ingredient, centiliters(995)))
    }

    @Test
    fun should_use_quantity_of_ingredient_with_exact_quantity_in_stock() {
        val ingredient = Ingredient("Milk")
        repository.upsert(StockItem(ingredient, centiliters(1_000)))

        stock.use(ingredient, centiliters(1_000))

        assertThat(repository.find(ingredient)).isNull()
    }

    @Test
    fun should_use_quantity_of_ingredient_with_insufficient_quantity_in_stock() {
        val ingredient = Ingredient("Milk")
        repository.upsert(StockItem(ingredient, centiliters(1_000)))

        stock.use(ingredient, centiliters(1_001))

        assertThat(repository.find(ingredient)).isNull()
    }

    @Test
    fun should_use_quantity_of_unknown_ingredient() {
        val ingredient = Ingredient("unknown")
        repository.delete(ingredient)

        stock.use(ingredient, centiliters(5))

        assertThat(repository.find(ingredient)).isNull()
    }

}
