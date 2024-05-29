plugins {
    id(ThunderbirdPlugins.Library.androidCompose)
}

android {
    namespace = "app.k9mail.feature.launcher"
    resourcePrefix = "launcher_"

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
    implementation(projects.app.ui.base)
    implementation(projects.feature.onboarding.main)
    implementation(projects.feature.settings.import)
    implementation(projects.feature.account.setup)
    implementation(projects.feature.account.edit)

    testImplementation(projects.core.ui.compose.testing)
}
