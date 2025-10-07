package com.blankon.sociotask.core.domain.auth.model

data class SignUpParams(
    val username: String?,
    val fullName: String?,
    val email: String,
    val password: String,
)