package com.blankon.sociotask.core.domain.auth.usecase

import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.auth.error.SignInError
import com.blankon.sociotask.core.domain.auth.model.SignInParams
import com.blankon.sociotask.core.domain.auth.model.User
import com.blankon.sociotask.core.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<User, SignInError> {
        if (email.isBlank()) {
            return Result.Error(SignInError.Validation.EmailBlank)
        }
        if (password.isBlank()) {
            return Result.Error(SignInError.Validation.PasswordBlank)
        }
        return when (val res = repository.signIn(SignInParams(email, password))) {
            is Result.Success -> Result.Success(res.data)
            is Result.Error -> Result.Error(SignInError.Data(res.error))
        }
    }
}