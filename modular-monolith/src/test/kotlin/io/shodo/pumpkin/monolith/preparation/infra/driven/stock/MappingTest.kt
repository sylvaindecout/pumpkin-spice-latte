package io.shodo.pumpkin.monolith.preparation.infra.driven.stock

import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.centiliters
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.grams
import io.shodo.pumpkin.monolith.shared.domain.Quantity.Companion.pieces
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingTest {

    @Test
    fun should_map_Quantity_to_DTO_with_amount() {
        val domainObject = grams(50)

        val dto = domainObject.toDto()

        assertThat(dto.amount).isEqualTo(50_000)
    }

    @Test
    fun should_map_Quantity_to_DTO_with_unit_of_measure() {
        val domainObject = centiliters(1)

        val dto = domainObject.toDto()

        assertThat(dto.unitOfMeasure).isEqualTo("mL")
    }

    @Test
    fun should_map_Quantity_to_DTO_with_no_unit_of_measure_if_missing() {
        val domainObject = pieces(1)

        val dto = domainObject.toDto()

        assertThat(dto.unitOfMeasure).isNull()
    }

}
