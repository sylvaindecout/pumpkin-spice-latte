package io.shodo.pumpkin.monolith.shared.domain

enum class UnitOfMeasure(val label: String, val abbreviation: String) {

    GRAMS("grams", "g"),
    MILLIGRAMS("milligrams", "mg"),
    CENTILITERS("centiliters", "cL"),
    MILLILITERS("milliliters", "mL");

    companion object {
        fun fromAbbreviation(abbreviation: String): UnitOfMeasure? = entries.find { it.abbreviation == abbreviation }
    }

}
