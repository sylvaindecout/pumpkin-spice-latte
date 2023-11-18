package io.shodo.pumpkin.monolith.preparation.infra.driven.stock

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class StockUpdateRequestBody(
    @field:NotBlank val ingredient: String?,
    @field:NotNull @field:Valid val usedQuantity: QuantityField?
)
