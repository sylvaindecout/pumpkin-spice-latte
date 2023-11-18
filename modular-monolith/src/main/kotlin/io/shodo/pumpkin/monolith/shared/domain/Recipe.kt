package io.shodo.pumpkin.monolith.shared.domain

data class Recipe(private val entries: Map<Ingredient, Quantity>) {

    init {
        if (entries.isEmpty()) {
            throw IllegalArgumentException("Recipe must not be empty")
        }
        if (entries.values.any { it.isZero }) {
            throw IllegalArgumentException("Quantities in a recipe must not be 0")
        }
    }

    fun asMap(): Map<Ingredient, Quantity> = entries

    companion object {
        fun from(vararg pairs: Pair<Ingredient, Quantity>) = Recipe(pairs.toMap())
    }

}
