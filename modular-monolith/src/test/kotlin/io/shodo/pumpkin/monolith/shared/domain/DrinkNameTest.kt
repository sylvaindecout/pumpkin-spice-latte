package io.shodo.pumpkin.monolith.shared.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class DrinkNameTest {

    @Test
    fun should_fail_to_initialize_from_blank_name() {
        assertThatIllegalArgumentException()
            .isThrownBy { DrinkName("   ") }
            .withMessage("Drink name must not be blank")
    }

    @Test
    fun should_display_as_string() {
        assertThat(DrinkName("Latte")).hasToString("Latte")
    }

}
