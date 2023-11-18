package io.shodo.pumpkin.monolith.catalogue.infra.driven.database

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.shodo.pumpkin.jooq.tables.Drink.Companion.DRINK
import io.shodo.pumpkin.jooq.tables.records.DrinkRecord
import io.shodo.pumpkin.monolith.catalogue.domain.Drink
import io.shodo.pumpkin.monolith.catalogue.domain.DrinkRepository
import io.shodo.pumpkin.monolith.shared.domain.DrinkName
import org.joda.money.Money
import org.jooq.DSLContext
import org.jooq.JSON.json
import org.springframework.stereotype.Component

@Component
class JdbcDrinkRepository(
    private val dsl: DSLContext,
    private val objectMapper: ObjectMapper
) : DrinkRepository {

    override fun findAll(): Collection<Drink> = dsl.selectFrom(DRINK)
        .fetch { record -> record.toDomain() }

    override fun find(name: DrinkName): Drink? = dsl.select()
        .from(DRINK)
        .where(DRINK.NAME.eq(name))
        .fetchOne { record -> (record as DrinkRecord).toDomain() }

    override fun upsert(drink: Drink) {
        drink.toRecord().let {
            dsl.insertInto(DRINK).set(it)
                .onDuplicateKeyUpdate().set(it)
                .execute()
        }
    }

    private fun DrinkRecord.toDomain() = Drink(
        this.name!!,
        Money.ofMinor(this.currency!!, this.unitPrice!!),
        objectMapper.readValue<Map<String, QuantityField>>(this.recipe!!.data()).toDomain()
    )

    private fun Drink.toRecord() = DrinkRecord(
        name = this.name,
        unitPrice = this.unitPrice.amountMinorLong,
        currency = this.unitPrice.currencyUnit,
        recipe = json(objectMapper.writeValueAsString(this.recipe.toRecord()))
    )
}
