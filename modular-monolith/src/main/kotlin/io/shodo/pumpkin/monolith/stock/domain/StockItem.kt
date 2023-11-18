package io.shodo.pumpkin.monolith.stock.domain

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity

data class StockItem(
    val ingredient: Ingredient,
    val currentQuantity: Quantity
)
