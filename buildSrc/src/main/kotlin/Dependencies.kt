import org.gradle.api.artifacts.dsl.DependencyHandler

//version constants for the Kotlin DSL dependencies
object Versions {
    //app level
    const val gradle = "7.2.2"
    const val kotlin = "1.7.10"
    const val kotlinComposeCompiler = "1.3.0"

    //libs
    const val coil = "2.2.2"
    const val coreKtx = "1.8.0"
    const val appcompat = "1.4.2"
    const val compose = "1.3.0"
    const val composeMaterial3 = "1.0.1"
    const val composeActivity = "1.5.1"
    const val composeNavigation = "2.5.1"
    const val koin = "3.2.0"
    const val moshi = "1.13.0"
    const val okhttp = "4.10.0"
    const val retrofit = "2.9.0"

    //test
    const val junit = "4.12"
    const val extJunit = "1.1.1"
    const val espresso = "3.2.0"
}

object AppDependencies {
    //std lib
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //android ui
    private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val coil = "io.coil-kt:coil-compose:${Versions.coil}"
    private val compose = listOf(
        "androidx.compose.ui:ui:${Versions.compose}",
        "androidx.compose.ui:ui-tooling:${Versions.compose}",
        "androidx.compose.foundation:foundation:${Versions.compose}",
        "androidx.compose.material:material:${Versions.compose}",
        "androidx.compose.material:material-icons-core:${Versions.compose}",
        "androidx.compose.material:material-icons-extended:${Versions.compose}",
        "androidx.compose.material3:material3:${Versions.composeMaterial3}",
        "androidx.activity:activity-ktx:${Versions.composeActivity}",
        "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    )
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    //di
    private val koinAndroid = listOf(
        "io.insert-koin:koin-android:${Versions.koin}",
        "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    )

    val network = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.okhttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}",
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}",
        "com.squareup.moshi:moshi:${Versions.moshi}",
        "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    )

    //test libs
    private const val junit = "junit:junit:${Versions.junit}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        addAll(compose)
        addAll(koinAndroid)
        addAll(network)
        add(coil)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}