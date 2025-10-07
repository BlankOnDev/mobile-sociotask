package com.blankon.sociotask.core.data.model.response

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("data") val data: T?,
    @SerializedName("message") val message: String?,
    @SerializedName("errors") val errors: Map<String, List<String>>?,
    @SerializedName("status") val status: String?
)

data class SignUpPayload(
    @SerializedName("user_id") val userId: Int,
)


data class SignInPayload(
    @SerializedName("token") val token: String
)