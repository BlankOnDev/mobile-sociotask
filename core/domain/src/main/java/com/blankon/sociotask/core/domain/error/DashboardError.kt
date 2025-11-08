package com.blankon.sociotask.core.domain.error

import com.blankon.sociotask.core.domain.utils.DomainError

sealed interface DashboardError : DomainError {
    // Validasi input saat membuat task
    sealed interface Validation : DashboardError {
        data object EmptyTitle : Validation
        data object EmptyDescription : Validation
        data object InvalidReward : Validation          // <= 0
        data object InvalidQuota : Validation           // <= 0
        data object InvalidDeadline : Validation        // format/masa lampau
        data object UnsupportedPaymentType : Validation
    }

    // Kondisi state bisnis
    data object InsufficientBalance : DashboardError   // saldo tak cukup utk budget task
    data object QuotaExceeded : DashboardError         // melewati plafon kuota
    data object BudgetExceeded : DashboardError        // melampaui total anggaran
    data object Closed : DashboardError                // task sudah ditutup
    data object NotAllowed : DashboardError            // user/role tak boleh
}