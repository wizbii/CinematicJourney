import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

val artifactId = "cinematic.journey"
val groupId = "com.wizbii"
val packageId = "$groupId.$artifactId"

val appVersionName = "0.0.5-SNAPSHOT"
val appVersionCode = appVersionName
    .removeSuffix("-SNAPSHOT")
    .split('.')
    .joinToString("") {
        it.padStart(3, '0')
    }
    .toInt()

val localProperties = gradleLocalProperties(rootDir)

group = groupId
version = appVersionName

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.build.config)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.atomicfu)
    alias(libs.plugins.kotlinx.atomicfu.internal)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.versions)
}

kotlin {

    jvmToolchain(libs.versions.java.get().toInt())

    androidTarget()
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    jvm("desktop") {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set("$packageId.MainKt")
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("mobile") {
                withAndroidTarget()
                withIos()
            }
        }
    }

    sourceSets.configureEach {
        languageSettings {
            optIn("androidx.compose.foundation.ExperimentalFoundationApi")
            optIn("androidx.compose.material3.ExperimentalMaterial3Api")
        }
    }

    targets.filterIsInstance<KotlinNativeTarget>().forEach { ios ->
        ios.binaries.framework {
            baseName = "ComposeApp"
        }
        sourceSets.configureEach {
            languageSettings {
                optIn("kotlinx.cinterop.BetaInteropApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }

}

repositories {
    mavenCentral()
    google()
}

dependencies {

    "commonMainApi"(libs.bundles.common.main.api)
    "commonMainImplementation"(libs.bundles.common.main.implementation)
    "androidMainImplementation"(libs.bundles.android.main)
    "iosMainImplementation"(libs.bundles.ios.main)
    "desktopMainImplementation"(libs.bundles.desktop.main)

    "commonTestImplementation"(libs.bundles.common.test)
    "desktopTestImplementation"(libs.bundles.desktop.test)

    "desktopMainImplementation"(compose.desktop.currentOs)

}

buildConfig {

    useKotlinOutput {
        topLevelConstants = true
    }

    packageName = packageId

    buildConfigField(
        name = "TMDB_API_KEY",
        value = localProperties.getProperty("tmdb.api.key"),
    )

}

tasks.withType<KotlinJvmTest> {
    useJUnitPlatform()
}

android {

    namespace = packageId

    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    defaultConfig {

        applicationId = packageId

        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()

        versionCode = appVersionCode
        versionName = appVersionName

    }

    signingConfigs.create("release") {
        storeFile = file("upload-keystore.jks")
        storePassword = localProperties.getProperty("android.upload.keystore.passphrase")
        keyAlias = "upload"
        keyPassword = localProperties.getProperty("android.upload.keystore.passphrase")
    }

    compileOptions {
        val javaVersion = JavaVersion.toVersion(libs.versions.java.get())
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }

}

compose.desktop {
    application {
        mainClass = "$packageId.MainKt"
    }
}

tasks.wrapper {
    gradleVersion = libs.versions.gradle.get()
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("$packageId.data")
        }
    }
}

task("setXcodeVersion") {
    group = "xcode"
    doLast {
        exec {
            workingDir("xcode")
            commandLine("agvtool", "new-version", "-all", appVersionCode)
        }
        exec {
            workingDir("xcode")
            commandLine("agvtool", "new-marketing-version", appVersionName)
        }
    }
}

tasks.withType<DependencyUpdatesTask> {

    fun isUnstable(version: String): Boolean {
        val lowerVersion = version.lowercase()
        val keywords = listOf("alpha", "beta", "dev", "RC").map(String::lowercase)
        for (keyword in keywords)
            if (keyword in lowerVersion)
                return true
        return false
    }

    rejectVersionIf {
        isUnstable(candidate.version) && !isUnstable(currentVersion)
    }

}
