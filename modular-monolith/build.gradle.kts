import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Nullability.NOT_NULL
import org.jooq.meta.jaxb.Property

val jodaMoneyVersion = "1.0.4"
val jooqLiquibaseVersion = "3.18.7"
val kotlinLoggingVersion = "3.0.5"
val liquibaseVersion = "4.25.0"
val mockitoKotlinVersion = "5.1.0"
val testcontainersVersion = "1.19.3"
val cucumberVersion = "7.15.0"

plugins {
  kotlin("jvm")
  kotlin("plugin.spring")
  id("org.springframework.boot")
  id("io.spring.dependency-management")
  id("nu.studer.jooq")
}

dependencies {
  implementation(kotlin("stdlib"))

  implementation("org.joda", "joda-money", jodaMoneyVersion)

  implementation("io.github.microutils", "kotlin-logging", kotlinLoggingVersion)

  implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")

  implementation("org.springframework.boot", "spring-boot-starter-web")
  implementation("org.springframework.boot", "spring-boot-starter-webflux")
  implementation("org.springframework.boot", "spring-boot-starter-validation")
  implementation("org.springframework.boot", "spring-boot-starter-actuator")
  implementation("org.springframework.boot", "spring-boot-starter-jooq")
  implementation("org.jooq", "jooq-meta-extensions-liquibase", jooqLiquibaseVersion)
  implementation("org.jooq", "jooq-kotlin")
  implementation("io.cucumber:cucumber-picocontainer:7.15.0")

  implementation("org.liquibase", "liquibase-core", liquibaseVersion)

  runtimeOnly("org.postgresql", "postgresql")

  jooqGenerator("org.jooq", "jooq-meta-extensions-liquibase")
  jooqGenerator("org.liquibase", "liquibase-core", liquibaseVersion)
  jooqGenerator("org.yaml", "snakeyaml")
  jooqGenerator("org.postgresql", "postgresql")

  testImplementation("org.springframework.boot", "spring-boot-starter-test") {
    exclude("org.junit.vintage", "junit-vintage-engine")
  }

  testImplementation("com.squareup.okhttp3", "mockwebserver")
  testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))
  testImplementation("org.testcontainers", "postgresql")

  testImplementation("org.mockito.kotlin", "mockito-kotlin", mockitoKotlinVersion)
  testImplementation("io.cucumber","cucumber-java",cucumberVersion)
  testImplementation("io.cucumber","cucumber-junit",cucumberVersion)
  testImplementation("io.cucumber","cucumber-java8",cucumberVersion)
  testImplementation("io.cucumber","cucumber-junit-platform-engine",cucumberVersion)
  testImplementation("org.junit.platform","junit-platform-suite")
  testImplementation(platform("io.cucumber:cucumber-bom:7.15.0"))
}

springBoot {
  mainClass.set("io.shodo.pumpkin.monolith.AppKt")
}

jooq {
  version.set(dependencyManagement.importedProperties["jooq.version"])
  edition.set(JooqEdition.OSS)

  configurations {
    create("main") {
      generateSchemaSourceOnCompilation.set(true)

      jooqConfiguration.apply {
        logging = Logging.WARN
        generator.apply {
          name = "org.jooq.codegen.KotlinGenerator"
          database.apply {
            name = "org.jooq.meta.extensions.liquibase.LiquibaseDatabase"
            properties.add(
              Property().withKey("rootPath")
                .withValue("$projectDir/src/main/resources")
            )
            properties.add(
              Property().withKey("scripts")
                .withValue("/db/changelog/db.changelog-master.yaml")
            )
            properties.add(
              Property().withKey("includeLiquibaseTables")
                .withValue("false")
            )
            forcedTypes = listOf(
              ForcedType().apply {
                userType = "io.shodo.pumpkin.monolith.shared.domain.DrinkName"
                converter = "io.shodo.pumpkin.monolith.shared.converter.DrinkNameConverter"
                includeExpression = "DRINK.NAME"
                nullability = NOT_NULL
              },
              ForcedType().apply {
                userType = "org.joda.money.CurrencyUnit"
                converter = "io.shodo.pumpkin.monolith.shared.converter.CurrencyUnitConverter"
                includeExpression = "DRINK.CURRENCY"
                nullability = NOT_NULL
              },
              ForcedType().apply {
                userType = "io.shodo.pumpkin.monolith.shared.domain.Ingredient"
                converter = "io.shodo.pumpkin.monolith.shared.converter.IngredientConverter"
                includeExpression = "STOCK_ITEM.INGREDIENT"
                nullability = NOT_NULL
              },
              ForcedType().apply {
                userType = "io.shodo.pumpkin.monolith.shared.domain.UnitOfMeasure"
                converter = "io.shodo.pumpkin.monolith.shared.converter.UnitOfMeasureConverter"
                includeExpression = "STOCK_ITEM.UNIT_OF_MEASURE"
              }
            )
          }
          target.apply {
            packageName = "io.shodo.pumpkin.jooq"
            directory = "src/generated/jooq"
          }
        }
      }
    }
  }
}
