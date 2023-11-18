package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity

fun interface Stock {
    fun hasEnoughOf(ingredient: Ingredient, requiredQuantity: Quantity): Boolean
}
