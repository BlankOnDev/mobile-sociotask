plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose.library)
}
android {
    namespace = "com.blankon.sociotask.core.designsystem"

}


dependencies {
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}