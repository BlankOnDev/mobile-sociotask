package com.blankon.sociotask.core.data.model.dto

import com.blankon.sociotask.core.data.model.response.TaskItem
import com.blankon.sociotask.core.domain.model.PaymentType
import com.blankon.sociotask.core.domain.model.Reward
import com.blankon.sociotask.core.domain.model.SocialPlatform
import com.blankon.sociotask.core.domain.model.Task
import com.blankon.sociotask.core.domain.model.TaskStatus
import com.blankon.sociotask.core.domain.model.platformColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun TaskItem.toDomain(): Task {
    val domainId = (this.id ?: 0).toString()
    val domainTitle = this.title ?: "Untitled Task"
    val domainDescription = this.description ?: "-"
    val domainQuota = this.maxParticipant?.toIntOrNull() ?: 0
    val domainDeadline = this.dueDate?.toLocalDateOrNull()

    val rewardPoints = this.rewardTask ?: 0
    val hasUsdt = this.rewardUsdt != null

    val domainPaymentType = if (hasUsdt) PaymentType.USDT else PaymentType.PointsOnly

    val domainReward = Reward(
        amount = rewardPoints,
        unit = if (hasUsdt) "USDT" else "Pts"
    )

    val domainPlatform = mapActionToPlatform(this.actionId)

    return Task(
        id = domainId,
        title = domainTitle,
        platform = domainPlatform,
        description = domainDescription,
        link = this.taskImage,
        platformTint = platformColor(domainPlatform),
        reward = domainReward,
        paymentType = domainPaymentType,
        quota = domainQuota,
        deadline = domainDeadline,
        ownerName = "Sociotask",
        status = TaskStatus.Available
    )
}


private fun String.toLocalDateOrNull(): LocalDate? {
    return try {
        // sesuaikan dengan format API kamu
        // misal: "2025-11-02" atau "2025-11-02 10:00:00"
        if (this.length == 10) {
            LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
        } else {
            // kalau ada jamnya, potong aja
            LocalDate.parse(this.substring(0, 10), DateTimeFormatter.ISO_DATE)
        }
    } catch (e: Exception) {
        null
    }
}


private fun mapActionToPlatform(actionId: Int?): SocialPlatform {
    return when (actionId) {
        1 -> SocialPlatform.Instagram
        2 -> SocialPlatform.X
        3 -> SocialPlatform.TikTok
        4 -> SocialPlatform.YouTube
        else -> SocialPlatform.Instagram // default
    }
}
