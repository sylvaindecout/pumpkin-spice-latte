package io.shodo.pumpkin.monolith.stock.infra.driving.rest

import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure
import io.shodo.pumpkin.monolith.stock.domain.StockItem

fun QuantityField.toDomain() = Quantity.of(
    amount = this.amount!!,
    unitOfMeasure = this.unitOfMeasure?.let { UnitOfMeasure.fromAbbreviation(it) }
)

fun StockItem.toDto() = StockItemResponseBody(
    ingredient = this.ingredient.name,
    currentQuantity = this.currentQuantity.toDto()
)

private fun Quantity.toDto() = QuantityField(
    amount = this.amount,
    unitOfMeasure = this.unitOfMeasure?.abbreviation
)
