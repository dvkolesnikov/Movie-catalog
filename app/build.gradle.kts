plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.appId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions.add(AppConfig.envDimension)
    productFlavors {
        create("dev") {
            applicationIdSuffix = ".$name"
            dimension = AppConfig.envDimension
            buildConfigField("String", "API_URL", "\"https://api.themoviedb.org/\"")
            buildConfigField("String", "API_KEY", "\"c9856d0cb57c3f14bf75bdc6c063b8f3\"")
        }

        create("prod") {
            applicationIdSuffix = ".$name"
            dimension = AppConfig.envDimension
            buildConfigField("String", "API_URL", "\"https://api.themoviedb.org/\"")
            buildConfigField("String", "API_KEY", "\"c9856d0cb57c3f14bf75bdc6c063b8f3\"")
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinComposeCompiler
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //app libs
    implementation(AppDependencies.appLibraries)
    //test libs
    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)
}