package com.blankon.sociotask.core.data.model.dto

import com.blankon.sociotask.core.data.model.response.ApiResponse
import com.blankon.sociotask.core.data.model.response.SignInPayload
import com.blankon.sociotask.core.data.model.response.SignUpPayload
import com.blankon.sociotask.core.domain.model.User


fun ApiResponse<SignInPayload>.toUserAfterSignIn(email: String): User =
    User(
        id = "",
        email = email,
        token = requireNotNull(data).token
    )

object UserMappers {

    fun ApiResponse<SignUpPayload>.toUserAfterSignUp(email: String): User =
        User(
            id = data?.userId.toString(),
            email = email,
            token = ""
        )
}