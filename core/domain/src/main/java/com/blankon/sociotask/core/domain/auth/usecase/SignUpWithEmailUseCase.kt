package com.blankon.sociotask.core.domain.auth.usecase

import com.blankon.sociotask.core.domain.auth.model.SignUpParams
import com.blankon.sociotask.core.domain.auth.model.User
import com.blankon.sociotask.core.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(signUpParams: SignUpParams): Flow<Result<User>> =
        repository.signUpWithEmail(signUpParams)
}