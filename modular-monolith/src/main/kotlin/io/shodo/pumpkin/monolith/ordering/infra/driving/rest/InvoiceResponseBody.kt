package io.shodo.pumpkin.monolith.ordering.infra.driving.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

data class InvoiceResponseBody(
    @field:NotNull @field:Valid val totalPrice: MoneyField?
)
