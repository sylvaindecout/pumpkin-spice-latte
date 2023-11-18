package io.shodo.pumpkin.monolith.shared.converter

import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.jooq.impl.AbstractConverter

class DrinkNameConverter : AbstractConverter<String, DrinkName>(String::class.java, DrinkName::class.java) {
    override fun from(databaseObject: String) = DrinkName(databaseObject)
    override fun to(userObject: DrinkName) = userObject.value
}
