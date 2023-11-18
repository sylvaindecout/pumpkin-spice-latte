package io.shodo.pumpkin.monolith.preparation.infra.driving.rest

import jakarta.validation.constraints.NotBlank

data class DrinkInPreparationResponseItem(
    @field:NotBlank val name: String,
    @field:NotBlank val customer: String
)
