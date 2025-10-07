import util.implementation
import util.libs

plugins {
    alias(libs.plugins.convention.application)
}

android {
    namespace = "com.blankon.sociotask"

    defaultConfig {
        applicationId = "com.blankon.sociotask"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.navigation)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)

    implementation(projects.feature.home)
    implementation(projects.feature.info)
    implementation(projects.feature.auth)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)


}