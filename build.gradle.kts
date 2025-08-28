plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.8.4" apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
    id("org.jlleitschuh.gradle.ktlint")version "12.1.0"
}
