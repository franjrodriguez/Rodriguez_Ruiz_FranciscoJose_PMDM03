plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.rodriguezruiz.pokedex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rodriguezruiz.pokedex"
        minSdk = 31
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

dependencies {

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Dependencias para Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // Dependencias para Firebase FireStore
    implementation("com.google.firebase:firebase-firestore")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // JSON Parsing
    implementation("com.google.code.gson:gson:2.6.1")
    implementation("com.squareup.retrofit2:converter-gson:2.1.0")

    // recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // NavigationController
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}