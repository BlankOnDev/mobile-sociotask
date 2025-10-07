package plugin.module

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.ConstantLibs.coreModules
import util.alias
import util.implementation
import util.libs

class FeatureModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                alias(libs.plugins.convention.android.library)
                alias(libs.plugins.convention.compose.library)
                alias(libs.plugins.convention.navigation)
                alias(libs.plugins.convention.hilt)

            }

            dependencies {
                implementation(project(coreModules[3]))
                implementation(project(coreModules[4]))
                coreModules.forEach { module ->
                    implementation(project(module))
                }
            }
        }
    }
}