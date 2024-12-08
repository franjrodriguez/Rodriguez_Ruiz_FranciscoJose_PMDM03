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
}

dependencies {
    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Dependencias para Firebase Authentication
    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")

    // Dependencias para Firebase FireStore
    implementation("com.google.firebase:firebase-firestore:25.1.1")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.1.0")

    // JSON Parsing
    implementation("com.google.code.gson:gson:2.6.1")
    implementation("com.squareup.retrofit2:converter-gson:2.1.0")

    // recyclerview
    implementation("com.android.support:recyclerview-v7:25.0.1")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}