package io.shodo.pumpkin.monolith.ordering.infra.driven.menu.client

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class MoneyField(
    @field:NotNull @field:Positive val amountMinor: Long?,
    @field:NotNull @field:Size(min = 3, max = 3) val currencyUnit: String?,
    @field:NotNull @field:PositiveOrZero val scale: Int?
)
