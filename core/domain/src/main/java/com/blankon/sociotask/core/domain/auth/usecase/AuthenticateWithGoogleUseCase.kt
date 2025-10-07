package com.blankon.sociotask.core.domain.auth.usecase

import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.SocialAuthError
import com.blankon.sociotask.core.domain.auth.model.User
import com.blankon.sociotask.core.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthenticateWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<User, SocialAuthError> {
        if (idToken.isBlank()) {
            return Result.Error(SocialAuthError.Validation.TokenBlank)
        }
        return when (val res = repository.authenticateWithGoogle(idToken)) {
            is Result.Success -> Result.Success(res.data)
            is Result.Error -> Result.Error(res.error)
        }
    }
}