package io.shodo.pumpkin.monolith.ordering.infra.driven.stock.client

import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure.MILLILITERS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingTest {

    @Test
    fun should_map_QuantityField_to_domain_with_amount() {
        val dto = QuantityField(50, "g")

        val domainObject = dto.toDomain()

        assertThat(domainObject.amount).isEqualTo(50_000)
    }

    @Test
    fun should_map_QuantityField_to_domain_with_unit_of_measure() {
        val dto = QuantityField(1, "cL")

        val domainObject = dto.toDomain()

        assertThat(domainObject.unitOfMeasure).isEqualTo(MILLILITERS)
    }

    @Test
    fun should_map_QuantityField_to_domain_with_no_unit_of_measure_if_missing() {
        val dto = QuantityField(1, unitOfMeasure = null)

        val domainObject = dto.toDomain()

        assertThat(domainObject.unitOfMeasure).isNull()
    }

}
