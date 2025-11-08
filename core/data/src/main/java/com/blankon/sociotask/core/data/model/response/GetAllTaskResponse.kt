package com.blankon.sociotask.core.data.model.response

import com.google.gson.annotations.SerializedName

data class ListTask(

    @field:SerializedName("meta")
    val meta: Meta? = null,

    @field:SerializedName("tasks")
    val tasks: List<TaskItem?>? = null
)


data class Meta(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("page")
    val page: Int? = null
)
