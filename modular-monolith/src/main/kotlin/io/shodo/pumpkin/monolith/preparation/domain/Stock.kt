package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity

fun interface Stock {
    fun notifyConsumption(ingredient: Ingredient, usedQuantity: Quantity)
}
