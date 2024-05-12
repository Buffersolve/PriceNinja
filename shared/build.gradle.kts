
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        val ktorVersion = "2.3.10"
//        val coroutinesVersion = "1.5.0-native-mt"
//        val serializationVersion = "1.2.2"

        commonMain.dependencies {

            // put your Multiplatform dependencies here
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-logging:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
//            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")

            implementation("io.insert-koin:koin-core:3.5.6")


        }
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
        }
        iosMain.dependencies {
            // put your iOS dependencies here
            implementation("io.ktor:ktor-client-darwin:$ktorVersion")
//            implementation("io.ktor:ktor-client-core-jvm:2.3.10")
//            implementation("io.ktor:ktor-client-okhttp:2.3.10")

        }
        jvmMain.dependencies {
            val exposed_version = "0.50.0"
            val h2_version = "2.2.220"

            implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
            implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
            implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
            implementation("com.h2database:h2:$h2_version")
        }
    }
}

android {
    namespace = "ua.priceninja.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
