package com.blankon.sociotask.core.data.model.request

data class CreateTaskRequest(
    val title: String,
    val description: String,
    val rewardTask: Int,
    val rewardUsdt: Double,
    val dueDate: String,
    val maxParticipant: String,
    val taskImage: String,
    val actionId: Int
)