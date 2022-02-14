buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(deps.Kotlin.Gradle.Plugin)
        classpath(deps.Android.Gradle.Plugin)
        classpath(deps.Google.Dagger.Hilt.Plugin)
        classpath(deps.Google.Secrets.Plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
