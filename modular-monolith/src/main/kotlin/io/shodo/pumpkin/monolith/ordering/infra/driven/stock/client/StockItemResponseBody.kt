package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class StockItemResponseBody(
    @field:NotBlank val ingredient: String?,
    @field:NotNull @field:Valid val currentQuantity: QuantityField?
)
