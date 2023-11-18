package io.shodo.pumpkin.monolith.stock.domain

import io.shodo.pumpkin.monolith.shared.domain.Ingredient

interface StockRepository {
    fun find(ingredient: Ingredient): StockItem?
    fun upsert(stockItem: StockItem)
    fun delete(ingredient: Ingredient)
}
