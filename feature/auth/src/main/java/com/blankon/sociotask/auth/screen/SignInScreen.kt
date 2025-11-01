package com.blankon.sociotask.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blankon.sociotask.auth.viewmodel.SignInEvent
import com.blankon.sociotask.auth.viewmodel.SignInIntent
import com.blankon.sociotask.auth.viewmodel.SignInUiState
import com.blankon.sociotask.auth.viewmodel.SignInViewModel
import com.blankon.sociotask.core.designsystem.component.HorizontalTextDivider
import com.blankon.sociotask.core.designsystem.component.SocialButtonsRow
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme
import com.blankon.sociotask.core.ui.EmailField
import com.blankon.sociotask.core.ui.ObserveAsEvents
import com.blankon.sociotask.core.ui.PasswordField
import com.blankon.sociotask.core.ui.SnackbarController
import com.blankon.sociotask.core.ui.SnackbarEvent

@Composable
fun SignInScreen(
    onNavigateHome: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SignInEvent.ShowMessage -> {
                val msg = event.message.asString(ctx)
                SnackbarController.send(
                    scope,
                    SnackbarEvent(message = msg)
                )
            }

            is SignInEvent.NavigateHome -> onNavigateHome(event.userId)
        }
    }

    LoginContent(
        state = state,
        onEmailChange = { viewModel.onIntent(SignInIntent.EmailChanged(it)) },
        onPasswordChange = { viewModel.onIntent(SignInIntent.PasswordChanged(it)) },
        onTogglePassword = { viewModel.onIntent(SignInIntent.TogglePassword) },
        onSubmit = { viewModel.onIntent(SignInIntent.Submit) },
        modifier = modifier
    )
}


@Composable
fun LoginContent(
    state: SignInUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onTogglePassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focus = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("LOGIN")
        Spacer(Modifier.height(24.dp))
        EmailField(
            value = state.email,
            onValueChange = onEmailChange,
            isError = state.emailError != null,
            errorText = state.emailError,
            enabled = !state.isLoading
        )

        Spacer(Modifier.height(12.dp))
        PasswordField(
            value = state.password,
            onValueChange = onPasswordChange,
            showPassword = state.showPassword,
            onTogglePassword = onTogglePassword,
            isError = state.passwordError != null,
            errorText = state.passwordError,
            enabled = !state.isLoading
        )
        Spacer(Modifier.height(24.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                focus.clearFocus()
                if (state.isFormValid && !state.isLoading) onSubmit()
            },
            enabled = state.isFormValid && !state.isLoading,
            colors = ButtonDefaults.buttonColors()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.height(18.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Login")
            }
        }
        Spacer(Modifier.height(30.dp))
        HorizontalTextDivider(text = "Or login with")
        Spacer(Modifier.height(16.dp))

        SocialButtonsRow(
            enabled = !state.isLoading,
            onGoogleClick = { /* TODO */ },
            onTwitterClick = { /* TODO */ }
        )
        Row {
            Text("Don't have an account ?")
            Text(text = "Sign Up Here")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    SociotaskTheme {
        LoginContent(
            state = SignInUiState(
                email = "Jhon Doe",
                password = "oiadhfoiasdf",
                showPassword = true,
                isLoading = false

            ),
            onEmailChange = {},
            onPasswordChange = {},
            onSubmit = {},
            onTogglePassword = {}
        )
    }
}

@Preview(showBackground = true, name = "Prefilled - Loading")
@Composable
private fun LoginContentPreview_Prefilled_Loading() {
    SociotaskTheme {
        LoginContent(
            state = SignInUiState(
                email = "johndoe",
                password = "secret123",
                showPassword = false,
                isLoading = true
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onSubmit = {},
            onTogglePassword = {}
        )
    }
}
