package io.shodo.pumpkin.monolith.shared.converter

import io.shodo.pumpkin.monolith.shared.domain.Ingredient
import org.jooq.impl.AbstractConverter

class IngredientConverter : AbstractConverter<String, Ingredient>(String::class.java, Ingredient::class.java) {
    override fun from(databaseObject: String) = Ingredient(databaseObject)
    override fun to(userObject: Ingredient) = userObject.name
}
