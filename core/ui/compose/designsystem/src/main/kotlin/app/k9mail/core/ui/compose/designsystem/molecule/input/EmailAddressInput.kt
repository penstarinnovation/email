package app.k9mail.core.ui.compose.designsystem.molecule.input

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.k9mail.core.ui.compose.designsystem.R
import app.k9mail.core.ui.compose.designsystem.atom.textfield.TextFieldOutlinedEmailAddress
import app.k9mail.core.ui.compose.theme.PreviewWithThemes

@Composable
fun EmailAddressInput(
    onEmailAddressChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    emailAddress: String = "",
    errorMessage: String? = null,
    isEnabled: Boolean = true,
    contentPadding: PaddingValues = inputContentPadding(),
) {
    InputLayout(
        modifier = modifier,
        contentPadding = contentPadding,
        errorMessage = errorMessage,
    ) {
        TextFieldOutlinedEmailAddress(
            value = emailAddress,
            onValueChange = onEmailAddressChange,
            label = stringResource(id = R.string.designsystem_molecule_email_address_input_label),
            isEnabled = isEnabled,
            hasError = errorMessage != null,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun EmailAddressInputPreview() {
    PreviewWithThemes {
        EmailAddressInput(
            onEmailAddressChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun EmailAddressInputWithErrorPreview() {
    PreviewWithThemes {
        EmailAddressInput(
            onEmailAddressChange = {},
            errorMessage = "Email address error",
        )
    }
}
