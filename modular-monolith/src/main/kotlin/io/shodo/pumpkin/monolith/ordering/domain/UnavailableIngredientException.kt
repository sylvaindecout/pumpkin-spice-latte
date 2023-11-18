package io.shodo.pumpkin.monolith.ordering.domain

import io.shodo.pumpkin.monolith.shared.domain.Ingredient

class UnavailableIngredientException(ingredient: Ingredient) :
    RuntimeException("Ingredient $ingredient is currently unavailable")
