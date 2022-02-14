package deps

object Android {
    object Gradle : Group("com.android.tools.build", "7.0.4") {
        val Plugin = artifact("gradle")
    }

    object X {
        val AppCompat = dependency("androidx.appcompat", "appcompat", "1.4.0")
        val Browser = dependency("androidx.browser", "browser", "1.2.0")
        val Core = dependency("androidx.core", "core-ktx", "1.7.0")

        object Compose : Group("androidx.compose", "1.1.0-beta03") {
            val Material = dependency("androidx.compose.material", "material", version)

            object Ui : Group("androidx.compose.ui", version) {
                val Ui = artifact("ui")
                val Tooling = artifact("ui-tooling")
                val Preview = artifact("ui-tooling-preview")
                val Material = artifact("material")

                object Test {
                    val JUnit = artifact("ui-test-junit4")
                    val Manifest = artifact("ui-test-manifest")
                }
            }

            object Runtime : Group("androidx.compose.runtime", version) {
                val LiveData = artifact("runtime-livedata")
            }
        }

        object Activity : Group("androidx.activity", "1.4.0") {
            val Compose = artifact("activity-compose")
        }

        object ArchCore : Group("androidx.arch.core", "2.1.0") {
            val Testing = artifact("core-testing")
        }

        object Hilt : Group("androidx.hilt", "1.0.0") {
            val Navigation = artifact("hilt-navigation-compose")
        }

        object Lifecycle : Group("androidx.lifecycle", "2.4.0") {
            val Compiler = artifact("lifecycle-compiler")

            object ViewModel {
                val Compose = dependency(groupId, "lifecycle-viewmodel-compose", "1.0.0-alpha7")
                val Ktx = artifact("lifecycle-viewmodel-ktx")
                val SavedState = artifact("lifecycle-viewmodel-savedstate")
            }

            object LiveData {
                val Ktx = artifact("lifecycle-livedata-ktx")
            }

            object Runtime {
                val Ktx = artifact("lifecycle-runtime-ktx")
            }
        }

        object Navigation : Group("androidx.navigation", "2.4.0-beta02") {
            val Compose = artifact("navigation-compose")
        }

        object Paging : Group("androidx.paging", "1.0.0-alpha14") {
            val Compose = artifact("paging-compose")
        }

        object Room : Group("androidx.room", "2.4.1") {
            val Runtime = artifact("room-runtime")
            val Compiler = artifact("room-compiler")
            val Ktx = artifact("room-ktx")
        }

        object Test {
            val Ext = dependency("androidx.test.ext", "junit", "1.1.3")
            val Espresso = dependency("androidx.test.espresso", "espresso-core", "3.4.0")
        }
    }
}

object Kotlin : Group("org.jetbrains.kotlin", "1.3.72") {
    object Coroutines : Group("org.jetbrains.kotlinx", "1.3.6") {
        val Android = artifact("kotlinx-coroutines-android")

        object Core {
            val Jvm = artifact("kotlinx-coroutines-core")
        }
    }

    object Gradle {
        val Plugin = dependency(groupId, "kotlin-gradle-plugin", "1.5.31")
    }

    object Test {
        val JUnit5 = artifact("kotlin-test-junit5")
        val Jvm = artifact("kotlin-test")
    }
}

object Google {
    object Dagger : Group("com.google.dagger", "2.40.5") {
        object Hilt {
            val Plugin = artifact("hilt-android-gradle-plugin")
            val Android = artifact("hilt-android")
            val Compiler = artifact("hilt-compiler")
        }
    }

    object Material : Group("com.google.android.material", "1.4.0") {
        val Components = artifact("material")
    }

    object Accompanist : Group("com.google.accompanist", "0.24.1-alpha") {
        val Permissions = artifact("accompanist-permissions")
    }

    object Secrets :
        Group("com.google.android.libraries.mapsplatform.secrets-gradle-plugin", "2.0.0") {
        val Plugin = artifact("secrets-gradle-plugin")
    }

    object PlayServices : Group("com.google.android.gms", "19.0.1") {
        val Location = artifact("play-services-location")
        val Maps = dependency(groupId, "play-services-maps", "18.0.2")
    }

    object Maps : Group("com.google.maps.android", "3.3.0") {
        val Ktx = artifact("maps-ktx")
        val Utils = artifact("maps-utils-ktx")
    }
}

object Coil : Group("io.coil-kt", "1.3.1") {
    val Compose = artifact("coil-compose")
}

object Square {
    object OkHttp3 : Group("com.squareup.okhttp3", "3.14.9") {
        val OkHttp = artifact("okhttp")
        val LoggingInterceptor = artifact("logging-interceptor")
    }

    object Retrofit2 : Group("com.squareup.retrofit2", "2.9.0") {
        val Retrofit = artifact("retrofit")
        val Gson = artifact("converter-gson")
    }
}

val JUnit = dependency("junit", "junit", "4.13.2")
val Robolectric = dependency("org.robolectric", "robolectric", "4.7")
