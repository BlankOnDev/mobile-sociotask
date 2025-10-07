package com.blankon.sociotask.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankon.sociotask.core.common.flow.asUiState
import com.blankon.sociotask.core.common.utils.state.UiState
import com.blankon.sociotask.core.common.utils.state.UiState.StateInitial
import com.blankon.sociotask.core.data.model.local.SampleModelEntity
import com.blankon.sociotask.core.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFetchApiViewModel @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {
    private val _ui = MutableStateFlow<UiState<List<SampleModelEntity>>>(StateInitial)
    val ui = _ui.asStateFlow()

    fun getSampleData() {
        viewModelScope.launch {
            repo.getListData() //Flow<ApiState<List<SampleModelEntity>>>
                .asUiState() //Flow<UiState<List<SampleModelEntity>>>
                .onStart { _ui.value = UiState.StateLoading }
                .catch { e ->
                    if (e is CancellationException) throw e
                    _ui.value = UiState.StateFailed(e)
                }
                .collect { ui ->
                    _ui.value = ui
                }
        }
    }
}