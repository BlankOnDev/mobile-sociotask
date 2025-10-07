plugins {
    alias(libs.plugins.convention.feature)
}
android{
    namespace = "com.blankon.sociotask.feature.auth"
}

dependencies{
    implementation(projects.core.domain)
}