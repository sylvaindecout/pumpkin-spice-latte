package io.shodo.pumpkin.monolith.catalogue.infra.driving.rest

import io.shodo.pumpkin.monolith.catalogue.domain.Catalogue
import io.shodo.pumpkin.monolith.catalogue.domain.Drink
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/catalogue")
class CatalogueController(private val catalogue: Catalogue) {

    @GetMapping("/drinks/")
    fun findAllDrinks(): Collection<CatalogueItemResponseBody> = catalogue
        .findAll()
        .map { it.toDto() }

    @GetMapping("/drinks/{name}")
    fun findDrink(@PathVariable name: String): CatalogueItemResponseBody? = catalogue
        .find(DrinkName(name))
        ?.toDto()

    @PutMapping("/drinks/{name}")
    @ResponseStatus(NO_CONTENT)
    fun upsert(@PathVariable name: String, @Valid @RequestBody update: CatalogueItemUpdateRequestBody) {
        catalogue.upsert(
            Drink(
                name = DrinkName(name),
                unitPrice = update.unitPrice!!.toDomain(),
                recipe = update.ingredients!!.toDomain()
            )
        )
    }

}
