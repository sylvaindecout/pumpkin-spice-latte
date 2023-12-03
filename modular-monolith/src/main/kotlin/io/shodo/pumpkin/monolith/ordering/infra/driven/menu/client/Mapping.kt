package io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client

import io.shodo.pumpkin.monolith.ordering.domain.menu.MenuItem
import io.shodo.pumpkin.monolith.shared.domain.*
import org.joda.money.CurrencyUnit
import org.joda.money.Money

fun MenuItemResponseBody.toDomain() = MenuItem(
    name = DrinkName(this.name!!),
    unitPrice = this.unitPrice!!.toDomain(),
    recipe = this.ingredients!!.toDomain()
)

private fun MoneyField.toDomain() = Money.ofMinor(
    CurrencyUnit.of(this.currencyUnit),
    this.amountMinor!!
)

private fun Map<String, QuantityField>.toDomain(): Recipe = this.entries
    .associate { Ingredient(name = it.key) to it.value.toDomain() }
    .let { Recipe(it) }

private fun QuantityField.toDomain() = Quantity.of(
    amount = this.amount!!,
    unitOfMeasure = this.unitOfMeasure?.let { UnitOfMeasure.fromAbbreviation(it) }
)
