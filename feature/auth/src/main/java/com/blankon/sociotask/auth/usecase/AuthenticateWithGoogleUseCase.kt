package com.blankon.sociotask.auth.usecase

import com.blankon.sociotask.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthenticateWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
//    suspend operator fun invoke(idToken: String): Result<User, SocialAuthDomainError> {
//        if (idToken.isBlank()) {
//            return Result.Error(SocialAuthDomainError.Validation.TokenBlank)
//        }
//        return when (val res = repository.authenticateWithGoogle(idToken)) {
//            is Result.Success -> Result.Success(res.data)
//            is Result.Error -> Result.Error(res.error)
//        }
//    }
}