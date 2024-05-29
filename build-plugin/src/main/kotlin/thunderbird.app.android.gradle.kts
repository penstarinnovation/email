plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("thunderbird.quality.detekt.typed")
}

android {
    configureSharedConfig()

    defaultConfig {
        targetSdk = ThunderbirdProjectConfig.androidSdkTarget
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = ThunderbirdProjectConfig.javaCompatibilityVersion.toString()
    }

    lint {
        checkDependencies = true
        lintConfig = file("${rootProject.projectDir}/config/lint/lint.xml")
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar)

    implementation(libs.bundles.shared.jvm.android.app)

    testImplementation(libs.bundles.shared.jvm.test)
}
