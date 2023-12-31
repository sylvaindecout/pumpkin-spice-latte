package io.shodo.pumpkin.monolith.shared.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign

@DomainDrivenDesign.ValueObject
@JvmInline
value class Ingredient(val name: String) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("Ingredient name must not be blank")
        }
    }

    override fun toString() = name

}
