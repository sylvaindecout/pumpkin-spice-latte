package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import io.shodo.pumpkin.monolith.shared.domain.Quantity
import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure

fun QuantityField.toDomain() = Quantity.of(
    amount = this.amount!!,
    unitOfMeasure = this.unitOfMeasure?.let { UnitOfMeasure.fromAbbreviation(it) }
)
