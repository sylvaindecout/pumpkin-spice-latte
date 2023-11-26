package io.shodo.pumpkin.monolith.menu.infra.driving.rest

import io.shodo.pumpkin.annotations.HexagonalArchitecture
import io.shodo.pumpkin.monolith.menu.domain.Drink
import io.shodo.pumpkin.monolith.menu.domain.Menu
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/menu")
@HexagonalArchitecture.LeftAdapter
class MenuController(private val menu: Menu) {

    @GetMapping("/drinks/")
    fun findAllDrinks(): Collection<MenuItemResponseBody> = menu
        .findAll()
        .map { it.toDto() }

    @GetMapping("/drinks/{name}")
    fun findDrink(@PathVariable name: String): MenuItemResponseBody? = menu
        .find(DrinkName(name))
        ?.toDto()

    @PutMapping("/drinks/{name}")
    @ResponseStatus(NO_CONTENT)
    fun upsert(@PathVariable name: String, @Valid @RequestBody update: MenuItemUpdateRequestBody) {
        menu.upsert(
            Drink(
                name = DrinkName(name),
                unitPrice = update.unitPrice!!.toDomain(),
                recipe = update.ingredients!!.toDomain()
            )
        )
    }

}
