package io.shodo.pumpkin.monolith.shared.domain

import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class RecipeTest {

    @Test
    fun should_fail_to_initialize_from_empty_list_of_entries() {
        val entries = emptyMap<Ingredient, Quantity>()

        assertThatIllegalArgumentException()
            .isThrownBy { Recipe(entries) }
            .withMessage("Recipe must not be empty")
    }

    @Test
    fun should_fail_to_initialize_from_entries_including_0_quantity() {
        val entries = mapOf(
            Ingredient("Coffee beans") to grams(5),
            Ingredient("Milk") to centiliters(0)
        )

        assertThatIllegalArgumentException()
            .isThrownBy { Recipe(entries) }
            .withMessage("Quantities in a recipe must not be 0")
    }

}
