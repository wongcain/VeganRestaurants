plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "cainwong.vegan"
        minSdk = 28
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
            }
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = deps.Android.X.Compose.version
    }
    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/licenses/**",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        )
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(deps.Android.X.Core)
    implementation(deps.Android.X.AppCompat)
    implementation(deps.Android.X.Compose.Ui.Ui)
    implementation(deps.Android.X.Compose.Ui.Preview)
    implementation(deps.Android.X.Compose.Ui.Tooling)
    implementation(deps.Android.X.Compose.Runtime.LiveData)
    implementation(deps.Android.X.Compose.Material)
    implementation(deps.Android.X.Activity.Compose)
    implementation(deps.Android.X.Paging.Compose)
    implementation(deps.Android.X.Navigation.Compose)
    implementation(deps.Android.X.Lifecycle.ViewModel.Compose)
    implementation(deps.Android.X.Lifecycle.ViewModel.Ktx)
    implementation(deps.Android.X.Lifecycle.ViewModel.SavedState)
    implementation(deps.Android.X.Lifecycle.LiveData.Ktx)
    implementation(deps.Android.X.Lifecycle.Runtime.Ktx)
    kapt(deps.Android.X.Lifecycle.Compiler)
    implementation(deps.Android.X.Room.Runtime)
    implementation(deps.Android.X.Room.Ktx)
    kapt(deps.Android.X.Room.Compiler)
    implementation(deps.Google.Material.Components)
    implementation(deps.Google.Accompanist.Permissions)
    implementation(deps.Google.PlayServices.Location)
    implementation(deps.Google.PlayServices.Maps)
    implementation(deps.Google.Maps.Ktx)
    implementation(deps.Google.Maps.Utils)
    implementation(deps.Google.Dagger.Hilt.Android)
    implementation(deps.Android.X.Hilt.Navigation)
    kapt(deps.Google.Dagger.Hilt.Compiler)
    implementation(deps.Square.OkHttp3.OkHttp)
    implementation(deps.Square.OkHttp3.LoggingInterceptor)
    implementation(deps.Square.Retrofit2.Retrofit)
    implementation(deps.Square.Retrofit2.Gson)
    implementation(deps.Coil.Compose)
    testImplementation(deps.JUnit)
    testImplementation(deps.Robolectric)
    testImplementation(deps.Android.X.ArchCore.Testing)
    testImplementation(deps.Android.X.Compose.Ui.Test.JUnit)
    androidTestImplementation(deps.Android.X.Test.Ext)
    androidTestImplementation(deps.Android.X.Test.Espresso)
    androidTestImplementation(deps.Android.X.Compose.Ui.Test.JUnit)
    debugImplementation(deps.Android.X.Compose.Ui.Test.Manifest)
}
