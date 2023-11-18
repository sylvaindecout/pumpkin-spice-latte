package io.shodo.pumpkin.monolith.shared.domain

import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure.GRAMS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UnitOfMeasureTest {

    @Test
    fun should_initialize_from_existing_abbreviation() {
        val abbreviation = "g"

        val unitOfMeasure = UnitOfMeasure.fromAbbreviation(abbreviation)

        assertThat(unitOfMeasure).isEqualTo(GRAMS)
    }

    @Test
    fun should_fail_to_initialize_from_unknown_abbreviation() {
        val abbreviation = "unknown"

        val unitOfMeasure = UnitOfMeasure.fromAbbreviation(abbreviation)

        assertThat(unitOfMeasure).isNull()
    }

}
