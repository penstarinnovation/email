package app.k9mail.feature.account.server.validation

import app.k9mail.core.common.coreCommonModule
import app.k9mail.feature.account.common.featureAccountCommonModule
import app.k9mail.feature.account.oauth.featureAccountOAuthModule
import app.k9mail.feature.account.server.certificate.featureAccountServerCertificateModule
import app.k9mail.feature.account.server.validation.domain.ServerValidationDomainContract
import app.k9mail.feature.account.server.validation.domain.usecase.ValidateServerSettings
import app.k9mail.feature.account.server.validation.ui.IncomingServerValidationViewModel
import app.k9mail.feature.account.server.validation.ui.OutgoingServerValidationViewModel
import com.fsck.k9.mail.store.imap.ImapServerSettingsValidator
import com.fsck.k9.mail.store.pop3.Pop3ServerSettingsValidator
import com.fsck.k9.mail.transport.smtp.SmtpServerSettingsValidator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureAccountServerValidationModule = module {
    includes(
        coreCommonModule,
        featureAccountCommonModule,
        featureAccountServerCertificateModule,
        featureAccountOAuthModule,
    )

    factory<ServerValidationDomainContract.UseCase.ValidateServerSettings> {
        ValidateServerSettings(
            authStateStorage = get(),
            imapValidator = ImapServerSettingsValidator(
                trustedSocketFactory = get(),
                oAuth2TokenProviderFactory = get(),
                clientIdAppName = get(named("ClientIdAppName")),
                clientIdAppVersion = get(named("ClientIdAppVersion")),
            ),
            pop3Validator = Pop3ServerSettingsValidator(
                trustedSocketFactory = get(),
            ),
            smtpValidator = SmtpServerSettingsValidator(
                trustedSocketFactory = get(),
                oAuth2TokenProviderFactory = get(),
            ),
        )
    }

    viewModel {
        IncomingServerValidationViewModel(
            validateServerSettings = get(),
            accountStateRepository = get(),
            authorizationStateRepository = get(),
            certificateErrorRepository = get(),
            oAuthViewModel = get(),
        )
    }

    viewModel {
        OutgoingServerValidationViewModel(
            validateServerSettings = get(),
            accountStateRepository = get(),
            authorizationStateRepository = get(),
            certificateErrorRepository = get(),
            oAuthViewModel = get(),
        )
    }
}
