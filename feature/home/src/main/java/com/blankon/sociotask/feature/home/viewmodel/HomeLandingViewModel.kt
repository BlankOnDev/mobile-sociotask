package com.blankon.sociotask.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankon.sociotask.core.domain.model.Balance
import com.blankon.sociotask.core.domain.model.Task
import com.blankon.sociotask.core.domain.model.TaskDraft
import com.blankon.sociotask.feature.home.usecase.GetDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val isLoading: Boolean = false,
    val balance: Balance = Balance(0),
    val tasks: List<Task> = emptyList(),
    val error: String? = null,
    val form: TaskDraft = TaskDraft(),
    val isFormOpen: Boolean = false,
    val creationInFlight: Boolean = false
)

sealed interface HomeEvent {
    data class ShowMessage(val message: String) : HomeEvent
    data class NavigateDetail(val id: String) : HomeEvent
}

@HiltViewModel
class HomeLandingViewModel @Inject constructor(
    private val getDashboard: GetDashboardUseCase,
    ) : ViewModel() {
    private val _ui = MutableStateFlow(HomeUiState(isLoading = true))
    val ui: StateFlow<HomeUiState> = _ui.asStateFlow()

    private val _events = Channel<HomeEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private fun Throwable.errorMessage(): String = message ?: toString()

    init {
        viewModelScope.launch {
            getDashboard()
                .onStart { _ui.update { it.copy(isLoading = true) } }
                .catch { _ui.update { it.copy(isLoading = false) } }
                .collect { dash ->
                    _ui.update {
                        it.copy(
                            isLoading = false,
                            balance = dash.balance,
                            tasks = dash.tasks,
                            error = null
                        )
                    }
                }
        }
    }


    fun openForm(open: Boolean) = _ui.update { it.copy(isFormOpen = open) }


    fun onFormChange(reducer: (TaskDraft) -> TaskDraft) {
        _ui.update { it.copy(form = reducer(it.form)) }
    }


    fun submit() {
        val draft = _ui.value.form
        if (!draft.isValid) {
            viewModelScope.launch { _events.send(HomeEvent.ShowMessage("Lengkapi form dulu")) }
            return
        }
        viewModelScope.launch {
//            _ui.update { it.copy(creationInFlight = true) }
//            when (val res = createTask(draft)) {
//                is Result.Success -> {
//                    _events.send(HomeEvent.ShowMessage("Task berhasil dibuat"))
//                    _ui.update {
//                        it.copy(
//                            creationInFlight = false,
//                            isFormOpen = false,
//                            form = TaskDraft()
//                        )
//                    }
//                }
//
//                is Result.Error -> {
//                    _ui.update { it.copy(creationInFlight = false) }
//                    _events.send(HomeEvent.ShowMessage("Gagal membuat task: ${res.throwable.message ?: "unknown"}"))
//                }
//            }
        }
    }

}

