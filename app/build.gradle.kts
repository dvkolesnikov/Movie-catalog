import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jmailen.kotlinter") version Versions.kotlinterPLugin
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

    signingConfigs {
        create("release") {
            val properties = Properties().apply {
                load(File("./app/signing.properties").reader())
            }
            storeFile = File(projectDir, properties.getProperty("storeFilePath"))
            storePassword = properties.getProperty("storePassword")
            keyPassword = properties.getProperty("keyPassword")
            keyAlias = properties.getProperty("keyAlias")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    flavorDimensions.add(AppConfig.envDimension)
    // Even though flavors are actually the same for now they can be used to organise work in more
    // safe and effective way if there are more than one backend instance.
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
    lint {
        abortOnError = true
        checkAllWarnings = true
        disable += setOf(
            "AllowBackup",
            "ContentDescription",
            "InvalidPackage",
            "SpUsage",
            "IconMissingDensityFolder",
            "SelectableText",
            "RtlCompat",
            "RtlEnabled",
            "RtlHardcoded",
            "RtlSymmetry",
            "MissingPrefix",
            "MissingRegistered",
            "LockedOrientationActivity",
        )
    }

    namespace = "com.noveogroup.moviecatalog"
}

dependencies {
    //app libs
    implementation(AppDependencies.appLibraries)
}