plugins {
    id(ThunderbirdPlugins.Library.androidCompose)
}

android {
    namespace = "app.k9mail.feature.account.oauth"
    resourcePrefix = "account_oauth_"

    buildTypes {
        debug {
            manifestPlaceholders["appAuthRedirectScheme"] = "FIXME: override this in your app project"
        }
        release {
            manifestPlaceholders["appAuthRedirectScheme"] = "FIXME: override this in your app project"
        }
    }
}

dependencies {
    implementation(projects.core.ui.compose.designsystem)
    implementation(projects.core.common)

    implementation(projects.mail.common)

    implementation(projects.feature.account.common)

    implementation(libs.appauth)
    implementation(libs.androidx.compose.material)
    implementation(libs.timber)

    testImplementation(projects.core.ui.compose.testing)
}
