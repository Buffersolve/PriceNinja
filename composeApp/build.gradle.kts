import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("plugin.serialization") version "1.9.22"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

//            implementation("io.insert-koin:koin-android:3.5.6")
            implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.0-alpha06")
//            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha06")
            implementation("com.liftric:kvault:1.11.0")

        }
        commonMain.dependencies {
            implementation("io.github.kevinnzou:compose-webview-multiplatform:1.9.10")


            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(projects.shared)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.tab.navigator)
            implementation(libs.voyager.transitions)

            val ktorVersion = "2.3.10"
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

            implementation("io.insert-koin:koin-compose:1.1.5")
            implementation("io.insert-koin:koin-core:3.5.6")

//            implementation("io.coil-kt.coil3:coil:3.0.0-alpha06")
//            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha06")

            implementation("io.coil-kt.coil3:coil-compose:3.0.0-alpha06")
            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha06")

            implementation("io.github.kalinjul.easyqrscan:scanner:0.1.3")


        }
        iosMain.dependencies {
            implementation("com.liftric:kvault:1.11.0")

            val ktorVersion = "2.3.10"

//            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha06")
            implementation("io.ktor:ktor-client-darwin:$ktorVersion")
        }
    }
}

android {
    namespace = "ua.priceninja"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "ua.priceninja"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation(libs.androidx.ui.android)
}

