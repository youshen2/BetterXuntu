plugins {
    id("com.android.application")
}

android {
    namespace = "moye.module.betterxuntu"
    compileSdk = 33

    defaultConfig {
        applicationId = "moye.module.betterxuntu"
        minSdk = 19
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 25
        versionCode = 240710
        versionName = "1.0"
        resConfigs("zh")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    compileOnly(files("libs/XposedBridgeApi.jar"))
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}