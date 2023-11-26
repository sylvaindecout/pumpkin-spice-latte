package io.shodo.pumpkin.monolith.preparation.domain

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.annotations.HexagonalArchitecture.Port.Type.DRIVEN
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity

@HexagonalArchitecture.Port(DRIVEN)
fun interface Stock {
    fun notifyConsumption(ingredient: Ingredient, usedQuantity: Quantity)
}
