plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
}
apply( "../shared_dependencies.gradle")

android {
    namespace = "com.pangeranmw.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    kapt {
        correctErrorTypes = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation (libs.kotlin.stdlib)

    //room
    api (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    androidTestImplementation (libs.androidx.room.testing)

    //retrofit)
    api (libs.retrofit)
    api (libs.converter.gson)
    api (libs.logging.interceptor)

    //coroutine
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.room.ktx)

    api (libs.androidx.lifecycle.livedata.ktx)
    api (libs.androidx.activity.ktx)
    api (libs.androidx.fragment.ktx)

    //rxjava
    api (libs.rxjava)
    api (libs.rxandroid)
    api (libs.rxbinding)
    api (libs.androidx.lifecycle.reactivestreams.ktx)
}