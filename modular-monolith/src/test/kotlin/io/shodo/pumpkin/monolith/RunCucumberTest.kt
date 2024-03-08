package io.shodo.pumpkin.monolith


import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.*

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
    @ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "io.shodo.pumpkin.monolith.stepdefs")
    @ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")

//@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "src/test/resources/features/rename.feature")
class RunCucumberTest {
}
