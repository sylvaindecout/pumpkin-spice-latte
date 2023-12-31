package io.shodo.pumpkin.monolith.stock.domain

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVING
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity

@HexagonalArchitecture.Port(DRIVING)
interface Stock {
    fun find(ingredient: Ingredient): StockItem?
    fun use(ingredient: Ingredient, usedQuantity: Quantity)
    fun upsert(ingredient: Ingredient, currentQuantity: Quantity)
}
