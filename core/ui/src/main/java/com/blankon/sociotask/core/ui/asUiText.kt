package com.blankon.sociotask.core.ui

import com.blankon.sociotask.core.domain.error.DataError
import com.blankon.sociotask.core.domain.error.SignInError
import com.blankon.sociotask.core.domain.error.SignUpError
import com.blankon.sociotask.core.ui.UiText.StringResource

fun DataError.toUiText(): UiText = when (this) {
    is DataError.Network.NoInternet -> StringResource(R.string.err_no_internet)
    is DataError.Network.Timeout -> StringResource(R.string.err_timeout)
    is DataError.Network.TooManyRequests -> StringResource(R.string.err_rate_limited)
    is DataError.Network.Unreachable -> StringResource(R.string.err_unreachable)
    is DataError.Network.Http -> when (code) {
        401 -> StringResource(R.string.err_unauthorized)
        403 -> StringResource(R.string.err_forbidden)
        404 -> StringResource(R.string.err_not_found)
        in 500..599 -> StringResource(R.string.err_server)
        else -> StringResource(R.string.err_unknown_with_code, arrayOf(code))
    }

    is DataError.Unknown -> StringResource(R.string.err_unknown)
    is DataError.Network.Auth.EmailNotVerified -> StringResource(R.string.err_auth_email_not_verified)
    is DataError.Network.Auth.InvalidCredentials -> StringResource(R.string.err_auth_invalid_credential)
    is DataError.Network.Auth.Unauthorized -> StringResource(R.string.err_auth_unauthorized)
    DataError.Network.ServerError -> StringResource(R.string.err_auth_unauthorized)
}

fun SignInError.toUiText(): UiText = when (this) {
    is SignInError.Validation.EmailBlank -> StringResource(R.string.err_email_blank)
    is SignInError.Validation.PasswordBlank -> StringResource(R.string.err_password_blank)
    is SignInError.Data -> this.cause.toUiText()
}

fun SignUpError.toUiText(): UiText = when (this) {
    is SignUpError.Validation.EmailBlank -> StringResource(R.string.err_email_blank)
    is SignUpError.Validation.FullNameBlank -> StringResource(R.string.err_full_name_blank)
    is SignUpError.Validation.PasswordBlank -> StringResource(R.string.err_password_blank)
    is SignUpError.Validation.UsernameBlank -> StringResource(R.string.err_username_blank)
    is SignUpError.Data -> this.cause.toUiText()
}