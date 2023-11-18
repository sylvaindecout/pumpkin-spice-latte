package io.shodo.pumpkin.monolith.ordering.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class CustomerTest {

    @Test
    fun should_fail_to_initialize_from_blank_name() {
        assertThatIllegalArgumentException()
            .isThrownBy { Customer("   ") }
            .withMessage("Customer name must not be blank")
    }

    @Test
    fun should_display_as_string() {
        assertThat(Customer("Vincent")).hasToString("Vincent")
    }

}
