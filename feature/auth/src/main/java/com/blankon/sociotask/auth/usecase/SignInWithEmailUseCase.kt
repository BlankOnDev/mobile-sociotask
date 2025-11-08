package com.blankon.sociotask.auth.usecase

import com.blankon.sociotask.core.domain.utils.Result
import com.blankon.sociotask.core.domain.error.SignInError
import com.blankon.sociotask.core.domain.model.SignInParams
import com.blankon.sociotask.core.domain.model.User
import com.blankon.sociotask.core.domain.repository.AuthRepository
import com.blankon.sociotask.core.domain.repository.SessionRepository
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<User, SignInError> {
        if (email.isBlank()) return Result.Error(SignInError.Validation.EmailBlank)
        if (password.isBlank()) return Result.Error(SignInError.Validation.PasswordBlank)
        return when (val res = repository.signInWithEmail(SignInParams(email, password))) {
            is Result.Success -> {
                sessionRepository.saveAccessToken(res.data.token)
                Result.Success(res.data)
            }

            is Result.Error -> Result.Error(SignInError.Data(res.error))
        }
    }
}