package com.blankon.sociotask.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.blankon.sociotask.core.navigation.route.HomeGraph.HomeDataTypeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val args by lazy { savedStateHandle.toRoute<HomeDataTypeRoute>() }
}