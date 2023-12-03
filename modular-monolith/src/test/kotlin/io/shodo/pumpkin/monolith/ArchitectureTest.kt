package io.shodo.pumpkin.monolith

import com.tngtech.archunit.base.DescribedPredicate.alwaysTrue
import com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchTests
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import io.shodo.pumpkin.rules.HexagonalArchitectureAnnotationRules
import org.junit.jupiter.api.Tag

@Tag("Architecture")
@AnalyzeClasses(packages = ["io.shodo.pumpkin.monolith"])
class ArchitectureTest {

    @ArchTest
    val subdomains_should_not_depend_on_one_another: ArchRule = slices()
        .matching("io.shodo.pumpkin.monolith.(*)..")
        .namingSlices("'$1' subdomain")
        .should().notDependOnEachOther()
        .ignoreDependency(alwaysTrue(), resideInAPackage("io.shodo.pumpkin.monolith.shared.."))
        .`as`("Subdomains should not depend on one another")
        .because("they are meant to be isolated. Communication should go through proper APIs.")

    @ArchTest
    val hexagonal_architecture_annotations: ArchTests = ArchTests.`in`(HexagonalArchitectureAnnotationRules.javaClass)

}
