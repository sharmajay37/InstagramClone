import org.jetbrains.kotlin.cli.js.klib.TopDownAnalyzerFacadeForJSIR.platform

// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    dependencies {
        // Google services classpath (needed for Firebase)
        classpath("com.google.gms:google-services:4.4.2")
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}