plugins {
    alias(libs.plugins.android.application)
     id("com.google.gms.google-services")
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
    implementation (platform("com.google.firebase:firebase-bom:33.7.0"))

    // Firebase Auth
    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.firebaseui:firebase-ui-auth:8.0.2")

    // Firestore
    implementation ("com.google.firebase:firebase-firestore")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // JSON Parsing
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.compose.ui:ui-viewbinding:1.7.6")
    implementation("com.google.android.material:material:1.12.0")

    // NavigationController
    implementation ("androidx.navigation:navigation-ui:2.7.7")
    implementation ("androidx.navigation:navigation-fragment:2.7.7")

    // Gestion de las imagenes desde internet
    implementation("com.squareup.picasso:picasso:2.8")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.preference)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}