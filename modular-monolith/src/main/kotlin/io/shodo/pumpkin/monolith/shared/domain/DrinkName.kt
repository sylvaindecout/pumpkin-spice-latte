package io.shodo.pumpkin.monolith.shared.domain

@JvmInline
value class DrinkName(val value: String) {

    init {
        if (value.isBlank()) {
            throw IllegalArgumentException("Drink name must not be blank")
        }
    }

    override fun toString() = value

}
