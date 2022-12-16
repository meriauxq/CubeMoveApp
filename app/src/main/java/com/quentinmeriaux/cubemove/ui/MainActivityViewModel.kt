package com.quentinmeriaux.cubemove.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quentinmeriaux.cubemove.model.ThemePreference
import com.quentinmeriaux.cubemove.repository.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainActivityViewModel(repository: UserDataRepository) : ViewModel() {

    val mainUiState: StateFlow<MainActivityUiState> =
        repository.userData
            .map { MainActivityUiState(theme = it.themePreference) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainActivityUiState(ThemePreference.SYSTEM_DEFAULT)
            )
}
