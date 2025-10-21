package com.blankon.sociotask.core.domain.dashboard.usecase

import com.blankon.sociotask.core.domain.AppClock
import com.blankon.sociotask.core.domain.DomainError
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.dashboard.error.DashboardError
import com.blankon.sociotask.core.domain.dashboard.model.Task
import com.blankon.sociotask.core.domain.dashboard.model.TaskDraft
import com.blankon.sociotask.core.domain.dashboard.repository.TaskRepository
import kotlinx.coroutines.flow.first
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repo: TaskRepository,
    @AppClock private val clock: Clock
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Task, DomainError> {
        val today: LocalDate = LocalDate.now(clock)
        // 1) Validasi form/domain (error bisnis)
        validateDraft(draft, today)?.let { return Result.Error(it) }

        // 2) Aturan bisnis tambahan: cek saldo vs total biaya (reward * quota)
        val balance = repo.observeBalance().first() // atau sediakan repo.getBalanceOnce()
        val totalCost = draft.rewardAmount * draft.quota
        if (totalCost > balance.points) return Result.Error(DashboardError.InsufficientBalance)

        // 3) Eksekusi repo (bisa gagal DataError.* atau sukses)
        return repo.createTask(draft)
    }

    private fun validateDraft(d: TaskDraft, today: LocalDate): DashboardError.Validation? {
        if (d.title.isBlank()) return DashboardError.Validation.EmptyTitle
        if (d.description.isBlank()) return DashboardError.Validation.EmptyDescription
        if (d.rewardAmount <= 0) return DashboardError.Validation.InvalidReward
        if (d.quota <= 0) return DashboardError.Validation.InvalidQuota
        d.deadline?.let { if (it.isBefore(today)) return DashboardError.Validation.InvalidDeadline }
        // contoh validasi payment type jika perlu:
        // if (d.paymentType == PaymentType.USDT && d.rewardAmount < MIN_USDT) return DashboardError.Validation.UnsupportedPaymentType
        return null
    }
}