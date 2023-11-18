package io.shodo.pumpkin.monolith.catalogue.infra.driven.database

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure

fun Map<String, QuantityField>.toDomain() = this.entries
    .associate { Ingredient(name = it.key) to it.value.toDomain() }
    .let { Recipe(it) }

private fun QuantityField.toDomain() = Quantity.of(
    amount = this.amount,
    unitOfMeasure = this.unitOfMeasure?.let { UnitOfMeasure.fromAbbreviation(it) }
)

fun Recipe.toRecord(): Map<String, QuantityField> = this.asMap()
    .map { it.key.name to it.value.toRecord() }
    .toMap()

private fun Quantity.toRecord() = QuantityField(
    amount = this.amount,
    unitOfMeasure = this.unitOfMeasure?.abbreviation
)
