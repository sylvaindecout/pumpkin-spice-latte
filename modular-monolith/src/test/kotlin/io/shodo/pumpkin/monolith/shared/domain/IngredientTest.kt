package io.shodo.pumpkin.monolith.shared.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class IngredientTest {

    @Test
    fun should_fail_to_initialize_from_blank_name() {
        assertThatIllegalArgumentException()
            .isThrownBy { Ingredient("   ") }
            .withMessage("Ingredient name must not be blank")
    }

    @Test
    fun should_display_as_string() {
        assertThat(Ingredient("Coffee beans")).hasToString("Coffee beans")
    }

}
