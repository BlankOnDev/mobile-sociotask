package com.blankon.sociotask.core.domain.auth.model

data class User(
    val id: String,
    val email: String,
    val token : String,
)