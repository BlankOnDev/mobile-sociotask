package com.blankon.sociotask.core.data.model.response

import com.google.gson.annotations.SerializedName

data class TaskItem(

    @SerializedName("reward_usdt") val rewardUsdt: Any? = null,

    @SerializedName("max_participant") val maxParticipant: String? = null,

    @SerializedName("updated_at") val updatedAt: String? = null,

    @SerializedName("user_id") val userId: Int? = null,

    @SerializedName("action_id") val actionId: Int? = null,

    @SerializedName("due_date") val dueDate: String? = null,

    @SerializedName("description") val description: String? = null,

    @SerializedName("created_at") val createdAt: String? = null,

    @SerializedName("id") val id: Int? = null,

    @SerializedName("reward_task") val rewardTask: Int? = null,

    @SerializedName("title") val title: String? = null,

    @SerializedName("task_image") val taskImage: String? = null
)
