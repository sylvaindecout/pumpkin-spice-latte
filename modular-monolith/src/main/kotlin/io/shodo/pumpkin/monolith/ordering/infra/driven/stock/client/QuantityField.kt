package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class QuantityField(
    @field:NotNull @field:Positive val amount: Int?,
    val unitOfMeasure: String?
)
