package io.shodo.pumpkin.monolith.ordering.infra.driven.preparation.client

import io.shodo.pumpkin.monolith.ordering.domain.preparation.Drink
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.shared.domain.Recipe

fun Drink.toDto() = DrinkRequestBody(
    name = this.name.value,
    ingredients = this.recipe.toDto(),
    customer = this.customer.name
)

private fun Recipe.toDto() = this.asMap().entries.associate { it.key.name to it.value.toDto() }

private fun Quantity.toDto() = QuantityField(
    amount = this.amount,
    unitOfMeasure = this.unitOfMeasure?.abbreviation
)
