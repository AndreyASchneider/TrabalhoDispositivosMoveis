plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.codigomorse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.codigomorse"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin);
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.sqlite:sqlite:2.1.0")
    implementation("androidx.sqlite:sqlite-framework:2.1.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.0-alpha01")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
}

secrets {
    propertiesFileName = "secrets.properties"

    defaultPropertiesFileName = "local.default.properties"
}