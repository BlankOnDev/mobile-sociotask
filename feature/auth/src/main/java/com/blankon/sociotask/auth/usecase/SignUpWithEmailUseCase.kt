package com.blankon.sociotask.auth.usecase

import com.blankon.sociotask.core.domain.utils.Result
import com.blankon.sociotask.core.domain.error.SignUpError
import com.blankon.sociotask.core.domain.model.RegisteredAccount
import com.blankon.sociotask.core.domain.model.SignUpParams
import com.blankon.sociotask.core.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        params: SignUpParams
    ): Result<RegisteredAccount, SignUpError> {
        if (params.username.isBlank()) return Result.Error(SignUpError.Validation.UsernameBlank)
        if (params.fullName.isBlank()) return Result.Error(SignUpError.Validation.FullNameBlank)
        if (params.email.isBlank()) return Result.Error(SignUpError.Validation.EmailBlank)
        if (params.password.isBlank()) return Result.Error(SignUpError.Validation.PasswordBlank)
        return when (val res = repository.signUpWithEmail(params)) {
            is Result.Success -> Result.Success(res.data)
            is Result.Error -> Result.Error(SignUpError.Data(res.error))

        }
    }
}