package com.blankon.sociotask.core.domain.dashboard.model

import java.time.LocalDate

data class Balance(val points: Int)
enum class SocialPlatform { Instagram, X, TikTok, YouTube }
enum class PaymentType { PointsOnly, USDT, BankTransfer }
data class Reward(
    val amount: Int,
    val unit: String = "Pts"
)

enum class TaskStatus { Available, Closed, Completed }

data class Task(
    val id: String,
    val title: String,
    val platform: SocialPlatform,
    val description: String,
    val link: String?,
    val reward: Reward,
    val paymentType: PaymentType,
    val quota: Int,
    val deadline: LocalDate?,
    val ownerName: String = "Sociotask",
    val status: TaskStatus = TaskStatus.Available
)

data class TaskDraft(
    val title: String = "",
    val platform: SocialPlatform = SocialPlatform.Instagram,
    val description: String = "",
    val link: String? = null,
    val rewardAmount: Int = 0,
    val paymentType: PaymentType = PaymentType.PointsOnly,
    val quota: Int = 10,
    val deadline: LocalDate? = null
) {
    val isValid: Boolean
        get() =
            title.isNotBlank() && description.isNotBlank() && rewardAmount > 0 && quota > 0
}