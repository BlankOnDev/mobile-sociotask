package com.blankon.sociotask.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blankon.sociotask.auth.viewmodel.SignUpEvent
import com.blankon.sociotask.auth.viewmodel.SignUpIntent
import com.blankon.sociotask.auth.viewmodel.SignUpUiState
import com.blankon.sociotask.auth.viewmodel.SignUpViewModel
import com.blankon.sociotask.core.ui.EmailField
import com.blankon.sociotask.core.ui.FullnameField
import com.blankon.sociotask.core.ui.PasswordField
import com.blankon.sociotask.core.ui.UsernameField
import com.blankon.sociotask.core.designsystem.theme.SociotaskTheme

@Composable
fun SignUpScreen(
    onNavigateLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.events.collect { e ->
            when (e) {
                is SignUpEvent.NavigateSignIn -> onNavigateLogin()
                is SignUpEvent.ShowMessage -> {}
            }
        }
    }
    SignUpContent(
        state = state,
        onEmailChange = { viewModel.onIntent(SignUpIntent.EmailChanged(it)) },
        onPasswordChange = { viewModel.onIntent(SignUpIntent.PasswordChanged(it)) },
        onSubmit = { viewModel.onIntent(SignUpIntent.Submit) },
        onTogglePassword = { viewModel.onIntent(SignUpIntent.TogglePassword) },
        onFullNameChanged = { viewModel.onIntent(SignUpIntent.FullNameChanged(it)) },
        onUsernameChanged = { viewModel.onIntent(SignUpIntent.UsernameChanged(it)) },
        onConfirmPasswordChange = { viewModel.onIntent(SignUpIntent.ConfirmPasswordChanged(it)) },
        modifier = modifier
    )
}

@Composable
fun SignUpContent(
    state: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onFullNameChanged: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onTogglePassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SignUp")
        FullnameField(
            value = state.fullName,
            onValueChange = onFullNameChanged,

            )
        UsernameField(
            value = state.username,
            onValueChange = onUsernameChanged,

            )
        EmailField(
            state.email,
            onValueChange = onEmailChange
        )
        PasswordField(
            state.password,
            onValueChange = onPasswordChange,
            showPassword = state.showPassword,
            onTogglePassword = onTogglePassword
        )
        PasswordField(
            state.password,
            onValueChange = onConfirmPasswordChange,
            showPassword = state.showPassword,
            onTogglePassword = onTogglePassword
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSubmit
        ) {
            Text("Signup")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPrev() {
    SociotaskTheme {
        SignUpContent(
            state = SignUpUiState(
                email = "Jhon Doe",
                password = "oiadhfoiasdf",
                showPassword = true,
            ),
            onTogglePassword = {},
            onSubmit = {},
            onEmailChange = {},
            onPasswordChange = {},
            onUsernameChanged = {},
            onFullNameChanged = {},
            onConfirmPasswordChange = {}
        )
    }
}