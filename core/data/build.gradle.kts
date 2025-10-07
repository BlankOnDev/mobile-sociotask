plugins {
    alias(libs.plugins.convention.data)
}

android {
    namespace = "com.blankon.sociotask.core.data"

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies{
    api(projects.core.domain)
    implementation("androidx.credentials:credentials:1.6.0-beta01")
    implementation("androidx.credentials:credentials-play-services-auth:1.6.0-beta01")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
}