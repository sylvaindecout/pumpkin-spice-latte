package io.shodo.pumpkin.monolith.ordering.domain.catalogue

class CatalogueAccessFailureException(cause: String) : RuntimeException("Catalogue access failed - $cause")
