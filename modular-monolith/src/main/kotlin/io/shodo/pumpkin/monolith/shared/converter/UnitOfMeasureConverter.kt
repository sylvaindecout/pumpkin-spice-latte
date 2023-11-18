package io.shodo.pumpkin.monolith.shared.converter

import io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure
import org.jooq.impl.AbstractConverter

class UnitOfMeasureConverter : AbstractConverter<String, UnitOfMeasure>(String::class.java, UnitOfMeasure::class.java) {
    override fun from(databaseObject: String?) = databaseObject?.let { UnitOfMeasure.fromAbbreviation(it) }
    override fun to(userObject: UnitOfMeasure?) = userObject?.abbreviation
}
