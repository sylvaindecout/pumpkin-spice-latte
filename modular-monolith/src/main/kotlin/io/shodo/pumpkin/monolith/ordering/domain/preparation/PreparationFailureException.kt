package io.shodo.pumpkin.monolith.ordering.domain.preparation

class PreparationFailureException(cause: String) : RuntimeException("Preparation notification failed - $cause")
