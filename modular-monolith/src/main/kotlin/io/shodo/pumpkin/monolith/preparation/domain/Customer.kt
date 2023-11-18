package io.shodo.pumpkin.monolith.preparation.domain

@JvmInline
value class Customer(val name: String) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("Customer name must not be blank")
        }
    }

    override fun toString() = name

}
