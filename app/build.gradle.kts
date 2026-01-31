plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.malakfinal"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.malakfinal"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cardview)
    // Gemini API dependencies
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.guava:guava:31.0.1-android")
    implementation("org.reactivestreams:reactive-streams:1.0.4")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //for room data base
    implementation( "androidx.room:room-runtime:2.8.3")
    annotationProcessor( "androidx.room:room-compiler:2.8.3")
    // optional - RxJava2 support for Room
    implementation ("androidx.room:room-rxjava2:2.8.3")
    // optional - RxJava3 support for Room
/* <<<<<<<<<<<<<<  ✨ Windsurf Command 🌟 >>>>>>>>>>>>>>>> */
    implementation("androidx.room:room-rxjava3:2.8.3")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:2.8.3")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:2.8.3")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:2.8.3")
/* <<<<<<<<<<  a1a7706c-a214-444f-9ca5-401b8229e5d1  >>>>>>>>>>> */

}
