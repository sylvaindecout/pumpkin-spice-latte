package io.shodo.pumpkin.monolith.shared.domain

import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.error
import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class QuantityTest {

    @Test
    fun should_fail_to_initialize_from_negative_amount() {
        assertThatIllegalArgumentException()
            .isThrownBy { Quantity.of(-1, GRAMS) }
            .withMessage("Quantity must not be negative")
    }

    @Test
    fun should_identify_quantity_with_0_amount_as_zero() {
        assertThat(Quantity.of(0, GRAMS).isZero).isTrue
    }

    @Test
    fun should_not_identify_quantity_with_positive_amount_as_zero() {
        assertThat(Quantity.of(1, GRAMS).isZero).isFalse
    }

    @Test
    fun should_multiply() {
        assertThat(Quantity.of(3, GRAMS) * 2)
            .isEqualTo(Quantity.of(6, GRAMS))
    }

    @Test
    fun should_fail_to_multiply_error() {
        assertThat((error() * 2).isError()).isTrue
    }

    @Test
    fun should_subtract() {
        assertThat(Quantity.of(3, GRAMS) - Quantity.of(2, GRAMS))
            .isEqualTo(Quantity.of(1, GRAMS))
    }

    @Test
    fun should_subtract_greater_value_to_0() {
        assertThat(Quantity.of(3, GRAMS) - Quantity.of(10, GRAMS))
            .isEqualTo(Quantity.of(0, GRAMS))
    }

    @Test
    fun should_fail_to_subtract_if_units_are_inconsistent() {
        val mass = Quantity.of(3, GRAMS)
        val volume = Quantity.of(2, CENTILITERS)

        val result = mass - volume

        assertThat(result.isError()).isTrue
    }

    @Test
    fun should_fail_to_subtract_error() {
        assertThat((aValidQuantity() - error()).isError()).isTrue
    }

    @Test
    fun should_fail_to_subtract_from_error() {
        assertThat((error() - aValidQuantity()).isError()).isTrue
    }

    @Test
    fun should_compare_quantities() {
        assertThat(Quantity.of(3, GRAMS) > Quantity.of(10, GRAMS)).isFalse
    }

    @Test
    fun should_fail_to_compare_quantities_if_units_are_inconsistent() {
        val mass = Quantity.of(3, GRAMS)
        val volume = Quantity.of(2, CENTILITERS)

        assertThatIllegalArgumentException()
            .isThrownBy { mass > volume }
            .withMessage("Operations do not apply to quantities with inconsistent units of measure ($MILLIGRAMS / $MILLILITERS)")
    }

    @Test
    fun should_fail_to_compare_quantities_if_this_is_an_error() {
        assertThatIllegalStateException()
            .isThrownBy { error() > aValidQuantity() }
            .withMessage("Invalid instance")
    }

    @Test
    fun should_fail_to_compare_quantities_if_other_is_an_error() {
        assertThatIllegalStateException()
            .isThrownBy { aValidQuantity() > error() }
            .withMessage("Invalid instance")
    }

    @Test
    fun should_display_as_string() {
        assertThat(Quantity.of(3, GRAMS)).hasToString("3000mg")
    }

    @Test
    fun should_display_as_string_with_no_unit_of_measure() {
        assertThat(Quantity.of(3, null)).hasToString("3 units")
    }

    @Test
    fun should_display_error_as_string() {
        assertThat(error()).hasToString("(error)")
    }

    private fun aValidQuantity() = Quantity.of(3, GRAMS)

}
