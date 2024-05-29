package app.k9mail.feature.onboarding.permissions.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.k9mail.core.android.permissions.PermissionState
import app.k9mail.core.ui.compose.common.annotation.PreviewDevices
import app.k9mail.core.ui.compose.common.mvi.observe
import app.k9mail.core.ui.compose.theme.K9Theme
import app.k9mail.feature.onboarding.permissions.ui.PermissionsContract.Effect
import app.k9mail.feature.onboarding.permissions.ui.PermissionsContract.Event
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.koinViewModel

@Composable
fun PermissionsScreen(
    viewModel: PermissionsContract.ViewModel = koinViewModel<PermissionsViewModel>(),
    onNext: () -> Unit,
) {
    val contactsPermissionLauncher = rememberLauncherForActivityResult(RequestPermission()) { success ->
        viewModel.event(Event.ContactsPermissionResult(success))
    }

    val notificationsPermissionLauncher = rememberLauncherForActivityResult(RequestPermission()) { success ->
        viewModel.event(Event.NotificationsPermissionResult(success))
    }

    val (state, dispatch) = viewModel.observe { effect ->
        when (effect) {
            Effect.RequestContactsPermission -> contactsPermissionLauncher.requestContactsPermission()
            Effect.RequestNotificationsPermission -> notificationsPermissionLauncher.requestNotificationsPermission()
            Effect.NavigateNext -> onNext()
        }
    }

    BackHandler {
        // no back navigation
    }

    LaunchedEffect(key1 = Unit) {
        dispatch(Event.LoadPermissionState)
    }

    PermissionsContent(
        state = state.value,
        onEvent = dispatch,
    )
}

private fun ManagedActivityResultLauncher<String, Boolean>.requestContactsPermission() {
    launch(Manifest.permission.READ_CONTACTS)
}

private fun ManagedActivityResultLauncher<String, Boolean>.requestNotificationsPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

@PreviewDevices
@Composable
internal fun PermissionScreenPreview() {
    K9Theme {
        PermissionsScreen(
            viewModel = PermissionsViewModel(
                checkPermission = { PermissionState.Denied },
                backgroundDispatcher = Dispatchers.Main.immediate,
            ),
            onNext = {},
        )
    }
}
