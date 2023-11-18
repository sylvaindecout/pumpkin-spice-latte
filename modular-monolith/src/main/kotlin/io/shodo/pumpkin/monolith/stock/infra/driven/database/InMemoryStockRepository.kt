package io.shodo.pumpkin.monolith.stock.infra.driven.database

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.stock.domain.StockItem
import io.shodo.pumpkin.monolith.stock.domain.StockRepository

class InMemoryStockRepository : StockRepository {

    private val stockItems = listOf(
        StockItem(Ingredient("Coffee beans"), grams(1_000)),
        StockItem(Ingredient("Milk"), centiliters(1_000))
    ).associateBy { it.ingredient }.toMutableMap()

    override fun find(ingredient: Ingredient): StockItem? = stockItems[ingredient]

    override fun upsert(stockItem: StockItem) {
        stockItems[stockItem.ingredient] = stockItem
    }

    override fun delete(ingredient: Ingredient) {
        stockItems.remove(ingredient)
    }

}
