package io.shodo.pumpkin.monolith.stock.infra.driving.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

data class StockItemUpdateRequestBody(
    @field:NotNull @field:Valid val currentQuantity: QuantityField?
)
