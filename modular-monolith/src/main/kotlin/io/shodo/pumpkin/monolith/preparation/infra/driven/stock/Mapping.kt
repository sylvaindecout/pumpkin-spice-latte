package io.shodo.pumpkin.monolith.preparation.infra.driven.stock

import io.shodo.pumpkin.monolith.shared.domain.Quantity

fun Quantity.toDto() = QuantityField(
    amount = this.amount,
    unitOfMeasure = this.unitOfMeasure?.abbreviation
)
