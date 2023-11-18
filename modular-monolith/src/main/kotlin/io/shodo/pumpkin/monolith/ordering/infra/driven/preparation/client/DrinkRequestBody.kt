package io.shodo.pumpkin.monolith.ordering.infra.driven.preparation.client

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class DrinkRequestBody(
    @field:NotBlank val name: String?,
    @field:NotEmpty @field:Valid val ingredients: Map<String, QuantityField>?,
    @field:NotBlank val customer: String?
)
