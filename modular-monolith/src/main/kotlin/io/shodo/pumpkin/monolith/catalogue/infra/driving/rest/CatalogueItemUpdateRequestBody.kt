package io.shodo.pumpkin.monolith.catalogue.infra.driving.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CatalogueItemUpdateRequestBody(
    @field:NotNull @field:Valid val unitPrice: MoneyField?,
    @field:NotEmpty @field:Valid val ingredients: Map<String, QuantityField>?
)