package com.blankon.sociotask.core.data.dashboard.repository

import com.blankon.sociotask.core.data.model.dto.toDomain
import com.blankon.sociotask.core.data.source.remote.ApiService
import com.blankon.sociotask.core.domain.utils.DomainError
import com.blankon.sociotask.core.domain.utils.Result
import com.blankon.sociotask.core.domain.utils.Result.Error
import com.blankon.sociotask.core.domain.error.DashboardError
import com.blankon.sociotask.core.domain.model.Balance
import com.blankon.sociotask.core.domain.model.Reward
import com.blankon.sociotask.core.domain.model.Task
import com.blankon.sociotask.core.domain.model.TaskDraft
import com.blankon.sociotask.core.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val api: ApiService
) : TaskRepository {
    private val balance = MutableStateFlow(Balance(points = 50_000))
//    private val tasks = MutableStateFlow(
//        listOf(
//            Task(
//                id = UUID.randomUUID().toString(),
//                title = "Instagram – Follow akun kami",
//                platform = SocialPlatform.Instagram,
//                description = "Follow akun Instagram @sociotask.id",
//                link = "https://instagram.com/sociotask.id",
//                reward = Reward(100),
//                paymentType = PaymentType.PointsOnly,
//                quota = 250,
//                deadline = LocalDate.now().plusDays(7),
//                platformTint = Color(0xFFD0BCFF)
//            ),
//            Task(
//                id = UUID.randomUUID().toString(),
//                title = "Instagram – Like & Comment",
//                platform = SocialPlatform.Instagram,
//                description = "Like dan comment postingan terbaru",
//                link = null,
//                reward = Reward(100),
//                paymentType = PaymentType.PointsOnly,
//                quota = 150,
//                deadline = LocalDate.now().plusDays(5),
//                platformTint = Color(0xFFD0BCFF)
//            ),
//            Task(
//                id = UUID.randomUUID().toString(),
//                title = "X (Twitter) – Repost",
//                platform = SocialPlatform.X,
//                description = "Repost tweet kampanye produk",
//                link = null,
//                reward = Reward(120),
//                paymentType = PaymentType.USDT,
//                quota = 100,
//                deadline = LocalDate.now().plusDays(3),
//                platformTint = Color(0xFFD0BCFF)
//            )
//        )
//
//    )

    override fun getAllBalance(): Flow<Balance> = balance

    override fun getAllTasks(page: Int): Flow<List<Task>> = flow {
        val response = api.getAllTasks(page)
        val listTask = response.data

        val domainTasks = listTask
            ?.tasks
            .orEmpty()
            .filterNotNull()
            .map { it.toDomain() }
        emit(domainTasks)
    }.flowOn(Dispatchers.IO)

    override suspend fun createTask(draft: TaskDraft): Result<Task, DomainError> {
        TODO("createTask belum diimplementasi")
//        // Validasi domain dulu (agar error yang keluar “bermakna”)
//        draft.title.ifBlank { return Error(DashboardError.Validation.EmptyTitle) }
//        draft.description.ifBlank {
//            return Error(
//                DashboardError.Validation.EmptyDescription
//            )
//        }
//        if (draft.rewardAmount <= 0) return Error(
//            DashboardError.Validation.InvalidReward
//        )
//        if (draft.quota <= 0) return Error(DashboardError.Validation.InvalidQuota)
//        draft.deadline?.let {
//            if (it.isBefore(LocalDate.now())) return Error(
//                DashboardError.Validation.InvalidDeadline
//            )
//        }
//
//        // Contoh aturan: total biaya = reward * quota tidak boleh melebihi balance
//        val totalCost = draft.rewardAmount * draft.quota
//        if (totalCost > balance.value.points) return Error(
//            DashboardError.InsufficientBalance
//        )
//
//        // (Simulasikan kemungkinan network)
//        // if (Random.nextInt(100) < 5) return Result.Error(DataError.Network.Timeout)
//
//        val created = Task(
//            id = UUID.randomUUID().toString(),
//            title = draft.title,
//            platform = draft.platform,
//            description = draft.description,
//            link = draft.link,
//            reward = Reward(draft.rewardAmount),
//            paymentType = draft.paymentType,
//            quota = draft.quota,
//            deadline = draft.deadline,
//        )
//        tasks.update { listOf(created) + it }
//        // Kurangi balance seolah dana di-hold
//        balance.update { it.copy(points = it.points - totalCost) }
//
//        return Result.Success(created)
    }
}