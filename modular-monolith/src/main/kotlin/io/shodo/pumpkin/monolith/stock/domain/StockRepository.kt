package io.shodo.pumpkin.monolith.stock.domain

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVEN
import io.shodo.pumpkin.monolith.shared.domain.Ingredient

@HexagonalArchitecture.Port(DRIVEN)
interface StockRepository {
    fun find(ingredient: Ingredient): StockItem?
    fun upsert(stockItem: StockItem)
    fun delete(ingredient: Ingredient)
}
