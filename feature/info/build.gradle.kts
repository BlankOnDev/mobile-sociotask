plugins {
    alias(libs.plugins.convention.feature)
}

android{
    namespace = "com.blankon.sociotask.feature.info"
}

dependencies {
    implementation(projects.core.ui)
}