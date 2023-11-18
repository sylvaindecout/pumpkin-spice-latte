package io.shodo.pumpkin.monolith.preparation.infra.driving.rest

import io.shodo.pumpkin.monolith.preparation.domain.Customer
import io.shodo.pumpkin.monolith.preparation.domain.Drink
import io.shodo.pumpkin.monolith.shared.domain.*

fun Drink.toDto() = DrinkInPreparationResponseItem(
    name = this.name.value,
    customer = this.customer.name
)

fun DrinkRequestBody.toDomain() = Drink(
    name = DrinkName(this.name!!),
    recipe = this.ingredients!!.toDomain(),
    customer = Customer(this.customer!!)
)

private fun Map<String, QuantityField>.toDomain(): Recipe = this.entries
    .associate { Ingredient(name = it.key) to it.value.toDomain() }
    .let { Recipe(it) }

private fun QuantityField.toDomain() = Quantity.of(
    amount = this.amount!!,
    unitOfMeasure = this.unitOfMeasure?.let { UnitOfMeasure.fromAbbreviation(it) }
)
