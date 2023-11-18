package io.shodo.pumpkin.monolith.ordering.infra.driving.rest

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class OrderRequestBody(
    @field:NotBlank val drink: String?,
    @field:NotNull @field:Positive val quantity: Int?,
    @field:NotBlank val customer: String?
)
