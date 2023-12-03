package io.shodo.pumpkin.monolith.preparation

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import io.shodo.pumpkin.rules.HexagonalArchitecture.Companion.hexagonalArchitecture
import org.junit.jupiter.api.Tag

@Tag("Architecture")
@AnalyzeClasses(
    packages = ["io.shodo.pumpkin.monolith.preparation"],
    importOptions = [DoNotIncludeTests::class]
)
class ArchitectureTest {

    @ArchTest
    val hexagonal_architecture = hexagonalArchitecture("io.shodo.pumpkin.monolith.preparation")
        .withDomainIn("domain")
        .withAdaptersIn("infra")
        .withAdditionalLanguagePackages("io.shodo.pumpkin.annotations..")
        .withAdditionalDomainPackages("io.shodo.pumpkin.monolith.shared.domain..")

}
