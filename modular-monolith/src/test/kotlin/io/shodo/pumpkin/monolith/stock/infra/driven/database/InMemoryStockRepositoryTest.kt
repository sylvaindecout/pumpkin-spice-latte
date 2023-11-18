package io.shodo.pumpkin.monolith.stock.infra.driven.database

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.stock.domain.StockItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InMemoryStockRepositoryTest {

    private val repository = InMemoryStockRepository()

    @Test
    fun should_find_existing_ingredient() {
        val ingredient = Ingredient("Milk")

        val stockItem = repository.find(ingredient)

        assertThat(stockItem).isEqualTo(
            StockItem(
                ingredient = ingredient,
                currentQuantity = centiliters(1_000)
            )
        )
    }

    @Test
    fun should_fail_to_find_unknown_ingredient() {
        val ingredient = Ingredient("unknown")

        val stockItem = repository.find(ingredient)

        assertThat(stockItem).isNull()
    }

    @Test
    fun should_update_existing_ingredient() {
        val ingredient = Ingredient("Milk")
        val stockItem = StockItem(ingredient, centiliters(97))

        repository.upsert(stockItem)

        assertThat(repository.find(ingredient)).isEqualTo(stockItem)
    }

    @Test
    fun should_add_unknown_ingredient() {
        val ingredient = Ingredient("unknown")
        val stockItem = StockItem(ingredient, centiliters(97))

        repository.upsert(stockItem)

        assertThat(repository.find(ingredient)).isEqualTo(stockItem)
    }

    @Test
    fun should_delete_existing_ingredient() {
        val ingredient = Ingredient("Milk")

        repository.delete(ingredient)

        assertThat(repository.find(ingredient)).isNull()
    }

    @Test
    fun should_delete_unknown_ingredient() {
        val ingredient = Ingredient("unknown")

        repository.delete(ingredient)

        assertThat(repository.find(ingredient)).isNull()
    }

}
