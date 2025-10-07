plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.compose.library)
}
android{
    namespace = "com.blankon.sociotask.core.ui"
}


dependencies {
    api(projects.core.designsystem)
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}