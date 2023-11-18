package io.shodo.pumpkin.monolith.ordering.infra.driven.catalogue.client

import io.shodo.pumpkin.monolith.ordering.domain.catalogue.CatalogueItem
import io.shodo.pumpkin.monolith.shared.domain.*
import org.joda.money.CurrencyUnit
import org.joda.money.Money

fun CatalogueItemResponseBody.toDomain() = CatalogueItem(
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
