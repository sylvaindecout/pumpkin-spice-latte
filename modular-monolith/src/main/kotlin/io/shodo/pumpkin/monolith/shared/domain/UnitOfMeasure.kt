package io.shodo.pumpkin.monolith.shared.domain

import io.shodo.pumpkin.annotations.DomainDrivenDesign

@DomainDrivenDesign.ValueObject
enum class UnitOfMeasure(val label: String, val abbreviation: String) {

    GRAMS("grams", "g"),
    MILLIGRAMS("milligrams", "mg"),
    CENTILITERS("centiliters", "cL"),
    MILLILITERS("milliliters", "mL");

    companion object {
        fun fromAbbreviation(abbreviation: String): UnitOfMeasure? = entries.find { it.abbreviation == abbreviation }
    }

}
