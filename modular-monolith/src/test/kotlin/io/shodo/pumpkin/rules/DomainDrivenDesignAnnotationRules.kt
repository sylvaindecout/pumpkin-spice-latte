package io.shodo.pumpkin.rules

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaModifier.FINAL
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent.violated
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.members
import io.shodo.pumpkin.annotations.DomainDrivenDesign
import kotlin.jvm.optionals.getOrNull

object DomainDrivenDesignAnnotationRules {

    @ArchTest
    val entities_should_have_an_ID: ArchRule = classes()
        .that().areMetaAnnotatedWith(DomainDrivenDesign.Entity::class.java)
        .should(haveAnId())

    @ArchTest
    val entity_IDs_should_be_declared_in_entities: ArchRule = members()
        .that().areAnnotatedWith(DomainDrivenDesign.Entity.Id::class.java)
        .should().beDeclaredInClassesThat().areMetaAnnotatedWith(DomainDrivenDesign.Entity::class.java)

    @ArchTest
    val services_should_be_immutable: ArchRule = classes()
        .that().areAnnotatedWith(DomainDrivenDesign.Service::class.java)
        .should().haveModifier(FINAL)
        .andShould().haveOnlyFinalFields()

    @ArchTest
    val value_objects_should_be_immutable: ArchRule = classes()
        .that().areAnnotatedWith(DomainDrivenDesign.ValueObject::class.java)
        .should().haveOnlyFinalFields()

    @ArchTest
    val repositories_should_have_an_aggregate_root: ArchRule = classes()
        .that().areAnnotatedWith(DomainDrivenDesign.Repository::class.java)
        .should(haveAnAggregateRoot())

    private fun haveAnId() = object : ArchCondition<JavaClass>("have an ID") {
        override fun check(item: JavaClass, events: ConditionEvents) {
            item.allMembers
                .count { it.isAnnotatedWith(DomainDrivenDesign.Entity.Id::class.java) }
                .let { if (it < 1) events.add(violated(item, "$item has $it Id(s)")) }
        }
    }

    private fun haveAnAggregateRoot() = object : ArchCondition<JavaClass>("have an AggregateRoot") {
        override fun check(item: JavaClass, events: ConditionEvents) {
            val annotationValue = item.annotations
                .find { it.rawType.isEquivalentTo(DomainDrivenDesign.Repository::class.java) }
                ?.let { it.getExplicitlyDeclaredProperty("value") as JavaClass }
            val aggregateRoot = annotationValue
                ?.tryGetAnnotationOfType(DomainDrivenDesign.AggregateRoot::class.java)
                ?.getOrNull()
            if (aggregateRoot == null) {
                val message = "Repository annotation of ${item.name} has value (${annotationValue?.name}) not annotated with AggregateRoot"
                events.add(violated(item, message))
            }
        }
    }

}
