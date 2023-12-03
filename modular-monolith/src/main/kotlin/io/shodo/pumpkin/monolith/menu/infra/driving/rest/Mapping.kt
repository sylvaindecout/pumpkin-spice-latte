package io.shodo.pumpkin.monolith.menu.infra.driving.rest

import io.shodo.pumpkin.monolith.menu.domain.Drink
import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.shared.domain.Recipe
import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure
import org.joda.money.CurrencyUnit
import org.joda.money.Money

fun MoneyField.toDomain(): Money = Money.ofMinor(
    CurrencyUnit.of(this.currencyUnit),
    this.amountMinor!!
)

fun Map<String, QuantityField>.toDomain(): Recipe = this.entries
    .associate { Ingredient(name = it.key) to it.value.toDomain() }
    .let { Recipe(it) }

private fun QuantityField.toDomain() = Quantity.of(
    amount = this.amount!!,
    unitOfMeasure = this.unitOfMeasure?.let { UnitOfMeasure.fromAbbreviation(it) }
)

fun Drink.toDto() = MenuItemResponseBody(
    name = this.name.value,
    unitPrice = this.unitPrice.toDto(),
    ingredients = this.recipe.toDto()
)

private fun Money.toDto() = MoneyField(
    amountMinor = this.amountMinorLong,
    currencyUnit = this.currencyUnit.code,
    scale = this.scale
)

private fun Recipe.toDto() = this.asMap().entries.associate { it.key.name to it.value.toDto() }

private fun Quantity.toDto() = QuantityField(
    amount = this.amount,
    unitOfMeasure = this.unitOfMeasure?.abbreviation
)
