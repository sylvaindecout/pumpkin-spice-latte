package io.shodo.pumpkin.rules

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.EvaluationResult
import com.tngtech.archunit.lang.Priority
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices

data class HexagonalArchitecture(
    private val overriddenDescription: String? = null,
    private val rootPackage: String,
    private val domainSubpackage: String = "domain",
    private val adaptersSubpackage: String = "infra",
    private val additionalLanguagePackages: Collection<String> = emptySet(),
    private val additionalDomainPackages: Collection<String> = emptySet(),
    private val allowEmptyShould: Boolean = true
) : ArchRule {

    private val domainPackage by lazy { "$rootPackage.$domainSubpackage" }
    private val adaptersPackage by lazy { "$rootPackage.$adaptersSubpackage" }
    private val languagePackages by lazy { additionalLanguagePackages + "java.." + "kotlin.." + "org.jetbrains.annotations.." }

    companion object {
        fun hexagonalArchitecture(rootPackage: String) = HexagonalArchitecture(rootPackage = rootPackage)
    }

    fun withDomainIn(domainSubpackage: String) = copy(domainSubpackage = domainSubpackage)

    fun withAdaptersIn(adaptersSubpackage: String) = copy(adaptersSubpackage = adaptersSubpackage)

    fun withAdditionalLanguagePackages(vararg additionalLanguagePackages: String) =
        copy(additionalLanguagePackages = additionalLanguagePackages.toSet())

    fun withAdditionalDomainPackages(vararg additionalDomainPackages: String) =
        copy(additionalDomainPackages = additionalDomainPackages.toSet())

    override fun getDescription() = overriddenDescription ?: listOf(
        "Hexagonal architecture with",
        " * Domain in $domainPackage",
        " * Adapters in $adaptersPackage"
    ).joinToString(System.lineSeparator())

    override fun evaluate(classes: JavaClasses) = EvaluationResult(this, Priority.MEDIUM)
        .apply { add(evaluateDomainIsIsolated(classes)) }
        .apply { add(evaluateAdaptersAreIndependent(classes)) }

    override fun `as`(newDescription: String?) = copy(overriddenDescription = newDescription)

    override fun check(classes: JavaClasses?) = ArchRule.Assertions.check(this, classes)

    override fun because(reason: String?): ArchRule = ArchRule.Factory.withBecause(this, reason)

    override fun allowEmptyShould(allowEmptyShould: Boolean) = copy(allowEmptyShould = allowEmptyShould)

    private fun evaluateDomainIsIsolated(importedClasses: JavaClasses) = classes()
        .that().resideInAPackage(domainPackage)
        .should().onlyDependOnClassesThat().resideInAnyPackage(
            *languagePackages.toTypedArray(),
            "$domainPackage..",
            *additionalDomainPackages.toTypedArray()
        )
        .`as`("The domain of the hexagon should not depend on infrastructure and technology")
        .because("business rules and technology have distinct lifecycles. Modifications to one should have minimal impact on the other.")
        .evaluate(importedClasses)

    private fun evaluateAdaptersAreIndependent(importedClasses: JavaClasses) = slices()
        .matching("$adaptersPackage.(*).(*)..").namingSlices("$1 adapter '$2'")
        .should().notDependOnEachOther()
        .`as`("Adapters around the domain of the hexagon should not depend on one another")
        .because("every business process should be under the control of the domain.")
        .evaluate(importedClasses)

}
