package io.shodo.pumpkin.monolith.shared.converter

import org.joda.money.CurrencyUnit
import org.jooq.impl.AbstractConverter

class CurrencyUnitConverter : AbstractConverter<String, CurrencyUnit>(String::class.java, CurrencyUnit::class.java) {
    override fun from(databaseObject: String): CurrencyUnit = CurrencyUnit.of(databaseObject)
    override fun to(userObject: CurrencyUnit): String = userObject.code
}
